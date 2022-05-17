package com.blog.base.dao;

import com.blog.base.pojo.Profile;
import com.blog.base.pojo.ProfileObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProfileObjDao extends JpaRepository<ProfileObj, String>, JpaSpecificationExecutor<ProfileObj> {

}
