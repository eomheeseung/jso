package example.jso.oauth2.userInfo;

import java.util.Map;

public abstract class Oauth2UserInfo {
    protected Map<String, Object> attributes;

    public Oauth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getNickName();

    public abstract String getImageUrl();
}
