package com.shea.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shea.admin.dao.entity.UserDO;
import com.shea.admin.dto.req.UserLoginReqDTO;
import com.shea.admin.dto.req.UserRegisterReqDTO;
import com.shea.admin.dto.req.UserUpdateReqDTO;
import com.shea.admin.dto.resp.UserLoginRespDTO;
import com.shea.admin.dto.resp.UserRespDTO;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/10 14:54
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 返回用户实体信息
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 用户名存在，返回true，否则返回false
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     *
     * @param userRegisterReqDTO 用户注册请求参数
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 修改用户信息
     *
     * @param userUpdateReqDTO 修改用户信息请求参数
     */
    void update(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 用户登录
     *
     * @param userLoginReqDTO 用户登录请求参数
     * @return 用户登录响应参数 token
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检查用户是否登录
     *
     * @param username 用户名
     * @param token    用户登录token
     * @return 用户是否登录标识
     */
    Boolean checkLogin(String username, String token);

    /**
     * 用户退出登录
     *
     * @param username 用户名
     * @param token    用户登录token
     */
    void logout(String username, String token);
}
