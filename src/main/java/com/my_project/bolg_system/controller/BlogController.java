package com.my_project.bolg_system.controller;


import com.my_project.bolg_system.model.BlogInfo;
import com.my_project.bolg_system.model.BlogListInfo;
import com.my_project.bolg_system.service.BlogService;
import com.my_project.bolg_system.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;

    /**
     * 获取博客列表
     * 后续根据页数信息更新，需要修改
     * @return
     */
    @RequestMapping("/getList")
    public BlogListInfo getBlogList(Integer page, Integer pageSize){
        return blogService.getList(page,pageSize);
    }

    /**
     * 根据博客Id获取博客详情
     * @param blogId
     * @return
     */
    @RequestMapping("/getDetail")
    public BlogInfo getBlogDetail(Integer blogId){
        return blogService.selectByBlogId(blogId);
    }

    /**
     * 发布博客
     * @param blogInfo
     * @param request
     * @return
     */
    @RequestMapping("/postBlog")
    public Boolean postBlog(BlogInfo blogInfo, HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        return blogService.postBlog(blogInfo, log_userId);
    }

    /**
     * 获取userid的博客
     * @param userId
     * @return
     */
    @RequestMapping("/getUserBlog")
    public List<BlogInfo> getuserBlog(Integer userId){
        return blogService.selectByUserId(userId);
    }

    /**
     * 更新博客
     * @param blogInfo
     * @return
     */
    @RequestMapping("/updateBlog")
    public Boolean updateBlog(BlogInfo blogInfo, HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        if (blogInfo==null || blogInfo.getId()==null){return false;}
        return blogService.updateBlog(blogInfo, log_userId);
    }

    /**
     * 认证更新权限
     * @param blogId
     * @param request
     * @return
     */
    @RequestMapping("/editPower")
    public Boolean updatePower(Integer blogId, HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        return blogService.updatePower(blogId, log_userId);
    }

    /**
     * 删除博客，逻辑删除
     * @param blogInfo
     * @return
     */
    @RequestMapping("/deleteBlog")
    public Boolean deleteBlog(BlogInfo blogInfo, HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        if (blogInfo==null || blogInfo.getId()==null){return false;}
        return blogService.deleteBlog(blogInfo, log_userId);
    }

}
