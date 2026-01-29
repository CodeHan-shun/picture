package com.hanshun.yupicturebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hanshun.yupicturebackend.annotation.AuthCheck;
import com.hanshun.yupicturebackend.common.DeleteRequest;
import com.hanshun.yupicturebackend.common.UserQueryRequest;
import com.hanshun.yupicturebackend.constant.UserConstant;
import com.hanshun.yupicturebackend.exception.BusinessException;
import com.hanshun.yupicturebackend.model.dto.user.UserAddRequest;
import com.hanshun.yupicturebackend.model.dto.user.UserLoginRequest;
import com.hanshun.yupicturebackend.model.dto.user.UserRegisterRequest;
import com.hanshun.yupicturebackend.model.dto.user.UserUpdateRequest;
import com.hanshun.yupicturebackend.model.entity.User;
import com.hanshun.yupicturebackend.model.vo.LoginUserVo;
import com.hanshun.yupicturebackend.model.vo.UserVO;
import com.hanshun.yupicturebackend.service.UserService;
import com.hanshun.yupicturebackend.common.BaseResponse;
import com.hanshun.yupicturebackend.common.ResultUtils;
import com.hanshun.yupicturebackend.exception.ErrorCode;
import com.hanshun.yupicturebackend.exception.ThrowUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hxs20
 * @version 1.0
 * @project yu-picture-backend
 * @date 2025/12/06 22:01:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/register")
    @AuthCheck(musRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        long result = userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.sucess(result);
    }


    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVo loginUserVo = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.sucess(loginUserVo);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVo> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.sucess(userService.getLoginUserVo(loginUser));
    }

    /**
     * 注销登录用户状态
     *
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLgout(HttpServletRequest request) {
        boolean result = userService.userLogout(request);
        return ResultUtils.sucess(result);
    }

    /**
     * 创建用户 管理员
     *
     * @param userAddRequest 添加用户请求
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(musRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(userAddRequest == null, ErrorCode.PARAMS_ERROR);

        User user = new User();

        BeanUtils.copyProperties(userAddRequest, user);
        final String DEFAULT_PWD = "12345678"; // 默认值
        String encryptPassword = userService.getEncryptPassword(DEFAULT_PWD);
        user.setUserPassword(encryptPassword);
        boolean save = userService.save(user);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR);
        return ResultUtils.sucess(user.getId());
    }

    /**
     * 根据id 获取用户（仅管理员）
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(musRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {

        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.sucess(user);
    }

    /**
     * 根据id 获取包装类（仅管理员）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(long id) {

        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.sucess(userService.getUserVo(user));

    }

    /**
     * 删除用户(管理员)
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(musRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {

        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean remove = userService.removeById(deleteRequest.getId());
        return ResultUtils.sucess(remove);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(musRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {

        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.sucess(true);
    }

    /**
     * 分页查询
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(musRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVoByPage(@RequestBody UserQueryRequest userQueryRequest) {

        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();

        Page<User> userPage = userService.page(new Page<>(current, pageSize),
                userService.getQueryWrapper(userQueryRequest));

        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVoList = userService.getUserVoList(userPage.getRecords());
        userVOPage.setRecords(userVoList);
        return ResultUtils.sucess(userVOPage);
    }


}
