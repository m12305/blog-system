package com.my_project.bolg_system.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`blog_article`")
public class BlogInfo {
    Integer id;
    String title;
    String content;
    String summary;
    String coverImg;
    Integer categoryId;
    Integer userId;
    Integer viewCount;
    Integer likeCount;
    Integer commentCount;
    Integer isTop;
    Integer isDraft;
    Integer status;
    Date createTime;
    Date updateTime;
    Integer deleted;
}
