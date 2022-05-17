package com.blog.base.controller;

import com.blog.base.pojo.Profile;
import com.blog.base.pojo.ProfileObj;
import com.blog.base.service.ProfileObjService;
import com.blog.base.service.ProfileService;
//import entity.StatusCode;
import entity.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.Result3;

import java.util.Map;
//Control layer for the forwarding of control methods, ultimately to the service layer
@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileObjService profileObjService;

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public Result update(@RequestBody Profile profile){
            String username = profile.getUsername();
            ProfileObj p = profile.getAvatar().get(0);
            p.setUsername(username);
            profileObjService.updateFile(p);
            profile.setUsername(username);
            profileService.update(profile);
            return new Result(1);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result3 findSearch(@RequestBody Map searchMap){
        Profile p = profileService.findSearch(searchMap);
        return new Result3(p.getUsername(), p.getGender(), p.getEmail(), p.getIntro(), profileObjService.findSearch1(searchMap));
    }

}
