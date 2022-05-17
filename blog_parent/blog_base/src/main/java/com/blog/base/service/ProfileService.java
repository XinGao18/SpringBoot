package com.blog.base.service;


import com.blog.base.dao.ProfileDao;
import com.blog.base.dao.ProfileObjDao;
import com.blog.base.pojo.Profile;


import com.blog.base.pojo.ProfileObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

//Service layer for the declaration of methods and, through calls to the connection pool, the manipulation of the database
@Service
@Transactional
public class ProfileService {
    @Autowired
    private ProfileDao profileDao;
    @Autowired
    private RedisTemplate redisTemplate;

    public void update(Profile profile){
        redisTemplate.delete("profile_" + profile.getUsername());
        profileDao.save(profile);
    }

    public Profile findSearch(Map searchMap){
        Specification<Profile> specification = createSpecification(searchMap);
        return profileDao.findAll(specification).get(0);
    }

    private Specification<Profile> createSpecification(Map searchMap) {
        return new Specification<Profile>() {
            @Override
            public Predicate toPredicate(Root<Profile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                    predicateList.add(cb.like(root.get("username").as(String.class), (String) searchMap.get("username")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

}
