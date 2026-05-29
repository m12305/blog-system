package com.my_project.bolg_system.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`blog_comment`")
public class CommentInfo {
    Integer id;
    Integer blogId;
    Integer userId;
    String avatar;
    String nickname;
    String content;
    Integer commentStatus;
    Date createTime;
    Date updateTime;
    Integer deleted;
}
