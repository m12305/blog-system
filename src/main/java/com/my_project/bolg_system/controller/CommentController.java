package com.my_project.bolg_system.controller;


import com.my_project.bolg_system.model.CommentInfo;
import com.my_project.bolg_system.service.CommentService;
import com.my_project.bolg_system.utils.GetFromHead;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 发表评论
     */
    @RequestMapping("/postComment")
    public Boolean postComment(CommentInfo commentInfo, HttpServletRequest request){
        Integer logUserId = GetFromHead.getLogUserId(request);
        return commentService.postComment(commentInfo,logUserId);
    }

    @RequestMapping("/getCommentList")
    public List<CommentInfo> getCommentList(Integer blogId){
        return commentService.getCommentList(blogId);
    }

    @RequestMapping("/deleteComment")
    public Boolean deleteComment(Integer commentId){
        return commentService.deleteComment(commentId);
    }

}
