package com.my_project.bolg_system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my_project.bolg_system.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("select * from blog_user where deleted =0 and username=#{username}")
    UserInfo selectByName(String username);

    @Select("select * from blog_user where deleted =0 and id=#{id}")
    UserInfo selectByUserId(Integer id);

    @Insert("INSERT INTO `blog_user` (username, password, nickname, email ) " +
            "VALUES ( #{username}, #{password}, #{nickname}, #{email})")
    Integer insertRegistUser(UserInfo userInfo);

    @Update("")
    UserInfo updateByUserId(UserInfo userInfo);

}
