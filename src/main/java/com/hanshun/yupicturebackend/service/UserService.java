package com.hanshun.yupicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanshun.yupicturebackend.common.UserQueryRequest;
import com.hanshun.yupicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hanshun.yupicturebackend.model.vo.LoginUserVo;
import com.hanshun.yupicturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hxs20
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2026-01-21 12:22:22
 */
public interface UserService extends IService<User> {
    /**
     * 获取加密密码
     *
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    /**
     * 注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 脱敏后用户登录信息
     *
     * @param user
     * @return
     */
    LoginUserVo getLoginUserVo(User user);

    /**
     * 脱敏后用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVo(User user);

    /**
     * 脱敏后用户信息列表
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVoList(List<User> userList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户登录态注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取查询条件
     * @param userQueryRequest 查询请求
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}
