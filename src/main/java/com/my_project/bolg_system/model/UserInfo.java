package com.my_project.bolg_system.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`blog_user`")
public class UserInfo {
    Integer id;
    String username;
    String password;
    String nickname;
    String email;
    String avatar;
    String bio;
    Integer status;
    Integer role;
    String lastLoginTime;
    Date createTime;
    Date updateTime;
    Integer deleted;
}
