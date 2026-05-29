package com.my_project.bolg_system.model;


import lombok.Data;

import java.util.List;

@Data
public class BlogListInfo {
    List<BlogInfo> blogList;
    Integer currentPage;
    Integer totalPages;
    Integer userBlogCount = Constant.USER_BLOG_COUNT;
    Integer categoryCount = Constant.CATEGORY_COUNT;
}
