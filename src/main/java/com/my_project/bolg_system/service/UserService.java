package com.my_project.bolg_system.service;


import com.my_project.bolg_system.mapper.UserMapper;
import com.my_project.bolg_system.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public UserInfo userLogin(String username, String password){
        UserInfo userInfo = userMapper.selectByName(username);
        //用户不存在
        if (userInfo==null){return null;}
        //密码错误
        if (!password.equals(userInfo.getPassword()) ){
            return null;
        }
        return userInfo;
    }

    public UserInfo selectByName(String username){
        UserInfo userInfo = userMapper.selectByName(username);
        //用户不存在
        //if (userInfo==null){return null;}
        return userInfo;
    }

    public UserInfo selectById(Integer id){
        UserInfo userInfo = userMapper.selectByUserId(id);
        //用户不存在
        //if (userInfo==null){return null;}
        return userInfo;
    }


    public Boolean register(UserInfo userInfo) {
        Integer L = userMapper.insertRegistUser(userInfo);
        if (L>0){
            userInfo.setPassword("*******");
            return true;
        }
        return false;
    }

    public UserInfo updateByUserId(UserInfo userInfo){
        userMapper.updateById(userInfo);
        return userMapper.selectByUserId(userInfo.getId());
    }

    public Boolean deleteUser(Integer id){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setDeleted(1);
        return userMapper.updateById(userInfo)==1;
    }

    public Boolean updatePassword(String oldPassword, String newPassword, Integer log_userId) {
        UserInfo userInfo = userMapper.selectById(log_userId);
        if (!userInfo.getPassword().equals(oldPassword)){
            return false;
        }
        userInfo.setPassword(newPassword);
        System.out.println("------------------------"+userMapper.updateById(userInfo));
        return userMapper.updateById(userInfo)==1;
    }
}
