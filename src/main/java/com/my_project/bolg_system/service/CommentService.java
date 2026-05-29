package com.my_project.bolg_system.service;


import com.my_project.bolg_system.mapper.CommentMapper;
import com.my_project.bolg_system.mapper.UserMapper;
import com.my_project.bolg_system.model.CommentInfo;
import com.my_project.bolg_system.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;

    public Boolean postComment(CommentInfo commentInfo, Integer logUserId) {
        UserInfo userInfo = userMapper.selectById(logUserId);
        commentInfo.setUserId(userInfo.getId());
        commentInfo.setNickname(userInfo.getNickname());
        commentInfo.setAvatar(userInfo.getAvatar());
        return commentMapper.insert(commentInfo)==1;
    }

    public List<CommentInfo> getCommentList(Integer blogId) {
        return commentMapper.selectByBlogId(blogId);
    }

    public Boolean deleteComment(Integer commentId) {
        CommentInfo commentInfo = commentMapper.selectById(commentId);
        commentInfo.setDeleted(1);
        return commentMapper.updateById(commentInfo)==1;
    }
}
