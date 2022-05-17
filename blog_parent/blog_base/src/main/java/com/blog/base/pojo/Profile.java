package com.blog.base.pojo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
//Model layer for object declarations
@Entity
@Table(name = "t_user")
public class Profile {
    @Id
    private String username;//user name
    private String gender;
    private String email;
    private String intro;//self-intro
    @Transient
    private List<ProfileObj> avatar;

    public List<ProfileObj> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<ProfileObj> avatar) {
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

}
