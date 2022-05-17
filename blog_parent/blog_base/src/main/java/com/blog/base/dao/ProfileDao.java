package com.blog.base.dao;

import com.blog.base.pojo.Profile;
import com.blog.base.pojo.ProfileObj;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
//Connection pools, generally used for database operations, comes with add, delete, change and check, where you can add your own special methods, similar to following friends, etc., multi-table join check, etc.
public interface ProfileDao extends JpaRepository<Profile, String>, JpaSpecificationExecutor<Profile> {


}
