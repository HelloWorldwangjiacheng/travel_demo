package com.travel.community.travel_demo.service;

import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
//        User dbUser = userMapper.findByAccountId(user.getAccountId());

        UserExample userExample = new UserExample();
        //这样可以拼接很多的sql语句，就不用我们自己去写了，就像是之前的通用的mapper
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
//            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);
        }else {
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setUserName(user.getUserName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
            //为什么要使用updateByExampleSelective因为这个是局部的变动的，
            // 参数1：这个参数是让传入一个对象,就是你要修改的那条数据所对应的对象，即你要更新的内容，
            // 参数2：传入xxxExample就可以，这里是基操看看上面的操作即可
        }


//        if (dbUser == null){
//            //插入
//            user.setGmtCreate(System.currentTimeMillis());
//            userMapper.githubInsert(user);
//        }else {
//            //更新
//            dbUser.setAvatarUrl(user.getAvatarUrl());
//            dbUser.setUserName(user.getUserName());
//            dbUser.setToken(user.getToken());
//            userMapper.githubUpdate(dbUser);
//        }
    }
}
