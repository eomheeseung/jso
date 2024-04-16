package example.jso.oauth2.service;

import example.jso.domain.user.SocialType;
import example.jso.domain.user.User;
import example.jso.domain.user.respository.UserRepository;
import example.jso.oauth2.CustomOAuth2User;
import example.jso.oauth2.OauthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOauth2UserService.loadUser() 실행 - Oauth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);

        String userNameAttributeName =
                userRequest
                        .getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();

        Map<String, Object> attributes = oauth2User.getAttributes();

        OauthAttributes extractAttributes = OauthAttributes.of(socialType, userNameAttributeName, attributes);

        User createUser = getUser(extractAttributes, socialType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createUser.getEmail(),
                createUser.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if (NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if (KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }

        return SocialType.GOOGLE;
    }

    private User getUser(OauthAttributes attributes, SocialType socialType) {
        User findUser =
                userRepository
                        .findBySocialTypeAndSocialId(socialType, attributes.getOauth2UserInfo().getId())
                        .orElse(null);

        if (findUser == null) {
            return saveUser(attributes, socialType);
        }

        return findUser;
    }

    private User saveUser(OauthAttributes attributes, SocialType socialType) {
        User createUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createUser);
    }
}
