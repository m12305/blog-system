package com.my_project.bolg_system.service;


import com.my_project.bolg_system.model.BlogListInfo;
import com.my_project.bolg_system.mapper.BlogMapper;
import com.my_project.bolg_system.model.BlogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogMapper blogMapper;

    public BlogListInfo getList(Integer page, Integer pageSize){
        BlogListInfo blogListInfo = new BlogListInfo();
        Integer offset = (page-1)* pageSize;
        List<BlogInfo>  list = blogMapper.selectByPage(offset, pageSize);
        Integer count = blogMapper.CountBlog();
        Integer totalPages = (count+pageSize-1)/pageSize;
        blogListInfo.setBlogList(list);
        blogListInfo.setCurrentPage(page);
        blogListInfo.setTotalPages(totalPages);
        return blogListInfo;
    }

    public Boolean postBlog(BlogInfo blogInfo, Integer log_userId) {
        //BlogInfo blogInfo = new BlogInfo();
        blogInfo.setUserId(log_userId);
        Integer n =  blogMapper.postBlog(blogInfo);
        return n == 1;
    }

    public List<BlogInfo> selectByUserId(Integer userId){
        return blogMapper.selectByUserId(userId);
    }

    public  BlogInfo selectByBlogId(Integer blogId){
        return blogMapper.getBlogDetail(blogId);
    }

    public Boolean updateBlog(BlogInfo blogInfo, Integer log_userId) {
        BlogInfo currentBlogInfo = blogMapper.selectById(blogInfo.getId());
        if (!currentBlogInfo.getUserId().equals(log_userId)){
            return false;
        }
        int n = blogMapper.updateById(blogInfo);
        return n == 1;
    }

    public Boolean deleteBlog(BlogInfo blogInfo, Integer log_userId) {
        BlogInfo currentBlogInfo = blogMapper.selectById(blogInfo.getId());
        if (!currentBlogInfo.getUserId().equals(log_userId)){
            return false;
        }
        int n = blogMapper.updateById(blogInfo);
        return n == 1;
    }

    public Boolean updatePower(Integer blogId, Integer log_userId) {
        BlogInfo currentBlogInfo = blogMapper.selectById(blogId);
        return currentBlogInfo.getUserId().equals(log_userId);
    }
}
