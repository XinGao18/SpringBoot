package util;

import com.blog.base.pojo.Profile;
import com.blog.base.pojo.ProfileObj;

public class Result3 {
    private String username;
    private String gender;
    private String email;
    private String intro;
    private Object avatar;// return data

    public Result3(String username, String gender, String email, String intro, Object avatar) {
        super();
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.intro = intro;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }
}
