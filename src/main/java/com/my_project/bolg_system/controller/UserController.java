package com.my_project.bolg_system.controller;


import com.my_project.bolg_system.model.Constant;
import com.my_project.bolg_system.model.Result;
import com.my_project.bolg_system.model.UserInfo;
import com.my_project.bolg_system.service.UserService;
import com.my_project.bolg_system.utils.GetFromHead;
import com.my_project.bolg_system.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public Result userLogin(@NotNull String username, @NotNull String password){
        UserInfo userInfo = userService.userLogin(username,password);
        if (userInfo==null){
            return Result.fail("密码错误或用户不存在");
        }
        Map<String,Object> cliam = new HashMap<>();
        cliam.put("log_userId", userInfo.getId());
        cliam.put("log_userName", userInfo.getUsername());

        String jwt = JWTUtils.genJwt(cliam);
        return Result.success(jwt);
    }

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    @RequestMapping("/register")
    public Boolean register(UserInfo userInfo){
        return userService.register(userInfo);
    }

    /**
     * 根据令牌获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfo")
    public UserInfo getUserInfo(HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        return userService.selectById(log_userId);
    }

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    @RequestMapping("/updateUserInfo")
    public UserInfo updateUserInfo(UserInfo userInfo, HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        UserInfo currentUser = userService.selectById(log_userId);
        userInfo.setId(currentUser.getId());
        return userService.updateByUserId(userInfo);
    }

    /**
     * 更新密码
     * 与更新用户信息类似但是多一步验证原密码是否正确
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/updatePassword")
    public Boolean updatePassword(String oldPassword, String newPassword, HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        return userService.updatePassword(oldPassword, newPassword, log_userId);
    }

    /**
     * 注销账户
     * 可以前端直接清楚登录token
     * @return
     */
    @RequestMapping("/delete")
    public Boolean logout(HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        return userService.deleteUser(log_userId);
    }

    /**
     * 用户上传头像
     */
    @RequestMapping("/updateAvatar")
    public UserInfo updateAvatar(@RequestPart("file")MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        Integer logUserId = GetFromHead.getLogUserId(request);
        // 2. 配置文件存储相关参数
        // 按日期分目录（如：2025/12/），方便管理文件
        //String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/"));
        String realDir = Constant.AVATAR_DATEDIR; // 最终存储目录：/root/Blog_Sys/avatars/

        // 3. 生成唯一文件名（避免文件名重复覆盖）
        String originalFilename = file.getOriginalFilename(); // 原始文件名（如：avatar.png）
        String fileSuffix = StringUtils.getFilenameExtension(originalFilename); // 获取文件后缀（png/jpg等）
        String uniqueFileName = UUID.randomUUID().toString() + "." + fileSuffix; // 唯一文件名：UUID+后缀

        // 4. 创建目录（如果目录不存在）
        File dir = new File(realDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 递归创建多级目录
        }

        // 5. 拼接完整文件路径，保存文件到服务器
        String filePath = realDir + uniqueFileName; // 完整路径：E:/Java_project/Blog_Sys/avatars/xxx-uuid.png
        file.transferTo(new File(filePath)); // 保存文件

        // 6. 生成可访问的头像链接（关键！存入数据库的是这个链接）
        //String serverDomain = "http://localhost:8080"; // 你的后端服务器域名+端口
        String serverDomain = "http://8.162.6.245:8080"; // 你的后端服务器域名+端口
        String avatarUrl = serverDomain + "/avatars/" + uniqueFileName; // 最终访问链接：http://xxx/avatars/xxx-uuid.png

        // 7. 更新用户头像链接到数据库
        UserInfo userInfo = new UserInfo();
        userInfo.setId(logUserId);
        userInfo.setAvatar(avatarUrl); // 存入String类型的链接
        return userService.updateByUserId(userInfo);
    }

}
