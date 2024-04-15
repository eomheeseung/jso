package example.jso.oauth2.userInfo;

import java.util.Map;

public class GoogleOauth2UserInfo extends Oauth2UserInfo {
    public GoogleOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public String getNickName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(attributes.get("picture"));
    }
}
