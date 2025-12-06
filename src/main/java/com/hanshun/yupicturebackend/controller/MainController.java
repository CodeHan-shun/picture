package com.hanshun.yupicturebackend.controller;

import com.hanshun.yupicturebackend.common.BaseResponse;
import com.hanshun.yupicturebackend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hxs20
 * @version 1.0
 * @project yu-picture-backend
 * @date 2025/12/06 22:01:53
 */
@RestController
@RequestMapping("/")
public class MainController {

    /**
     * 健康检查：快速验证后端服务是否正常运行
     *
     * @return
     */
    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.sucess("ok");
    }
}
