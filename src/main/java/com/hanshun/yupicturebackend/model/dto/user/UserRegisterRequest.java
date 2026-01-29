package com.hanshun.yupicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hxs20
 * @version 1.0
 * @project yu-picture-backend
 * @date 2026/01/21 12:37:04
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -6027227293849681380L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}

