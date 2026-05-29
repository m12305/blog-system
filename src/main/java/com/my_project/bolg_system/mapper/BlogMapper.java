package com.my_project.bolg_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my_project.bolg_system.model.BlogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<BlogInfo> {

    @Select("select * from blog_article where deleted != 1 ORDER BY id DESC LIMIT #{offset}, #{size};")
    List<BlogInfo> selectByPage(Integer offset, Integer size);

    @Insert("INSERT INTO `blog_article`  (title, content, summary, category_id, user_id )  VALUES (#{title}, #{content}, #{summary}, #{categoryId}, #{userId})")
    Integer postBlog(BlogInfo blogInfo);

    @Select("select * from blog_article where id = #{blogId}")
    BlogInfo getBlogDetail(Integer blogId);

    @Select("select * from blog_article where deleted != 1 and user_id = #{userId}")
    List<BlogInfo> selectByUserId(Integer userId);

    @Select("SELECT COUNT(*) AS total FROM blog_article where deleted != 1;")
    Integer CountBlog();

}
