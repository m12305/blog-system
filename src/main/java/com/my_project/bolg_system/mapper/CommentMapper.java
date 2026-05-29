package com.my_project.bolg_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my_project.bolg_system.model.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<CommentInfo> {
    @Select("SELECT * " +
            "FROM blog_comment " +
            "WHERE blog_id = #{blogID} " +  // 匹配方法参数 blogID
            "  AND comment_status = 0 " +  // 只查询「正常状态」的评论（过滤禁用评论）
            "  AND deleted = 0 " +         // 只查询「未删除」的评论（过滤逻辑删除数据）
            "ORDER BY create_time DESC")   // 按创建时间倒序（最新评论在前）
    List<CommentInfo> selectByBlogId(Integer blogId);
}
