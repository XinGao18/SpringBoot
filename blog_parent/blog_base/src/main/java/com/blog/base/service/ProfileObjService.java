package com.blog.base.service;

import com.blog.base.dao.ProfileObjDao;
import com.blog.base.pojo.ProfileObj;
import org.apache.commons.codec.binary.Base64;
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
import java.io.OutputStream;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProfileObjService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ProfileObjDao profileObjDao;
    @Autowired
    private RedisTemplate redisTemplate;


    public void updateFile(ProfileObj p) {
        redisTemplate.delete("profile_" + p.getName());
        profileObjDao.save(p);
        upload(p);
    }

    public void upload(ProfileObj p) {
        String data = p.getThumburl();
        String base64Data = data.split(",")[1];
        byte[] bytes = Base64.decodeBase64(base64Data);

        OutputStream fos = null;
        long id = idWorker.nextId();
        p.setName(id + ".jpg");
        File filepath = new File("C:\\upload\\");
        File picfilepath = new File("C:\\upload\\" + p.getName());
        String path = String.valueOf(picfilepath);
        if (!filepath.exists()){
            filepath.mkdirs();
        }
        try {
            fos = new FileOutputStream(picfilepath);
            fos.write(bytes);
            fos.close();
        }catch (IOException e){

        }
    }

    public ProfileObj findSearch1(Map searchMap){
        Specification<ProfileObj> specification = createSpecification1(searchMap);
        return profileObjDao.findAll(specification).get(0);
    }

    private Specification<ProfileObj> createSpecification1(Map searchMap) {
        return new Specification<ProfileObj>() {
            @Override
            public Predicate toPredicate(Root<ProfileObj> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                    predicateList.add(cb.like(root.get("username").as(String.class), (String) searchMap.get("username")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
