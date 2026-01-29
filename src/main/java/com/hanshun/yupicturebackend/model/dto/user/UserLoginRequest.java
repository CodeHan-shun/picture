package com.hanshun.yupicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hxs20
 * @version 1.0
 * @project yu-picture-backend
 * @date 2026/01/21 13:41:31
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}
