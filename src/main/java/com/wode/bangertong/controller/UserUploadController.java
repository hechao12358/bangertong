package com.wode.bangertong.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wode.bangertong.entity.UserUpload;
import com.wode.bangertong.model.Result;
import com.wode.bangertong.service.UserUploadService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户下载表(UserUpload)表控制层
 *
 * @author makejava
 * @since 2024-07-06 17:50:46
 */
@RestController
@RequestMapping("userUpload")
public class UserUploadController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserUploadService userUploadService;

    /**
     * 查询我的下载列表
     * @param request
     * @return
     */
    @GetMapping("/selectMyUpload")
    public Result selectMyUpload(HttpServletRequest request) {
        String userId = request.getHeader("token");
        if (null == userId || userId.isEmpty()) {
            return new Result(Result.RESULT_OK,null,new Date().getTime());
        }
        List<UserUpload> list = userUploadService.list(Wrappers.<UserUpload>lambdaQuery()
                .eq(UserUpload::getUserId, userId)
                .eq(UserUpload::getIsSucceed, 0));
        list = list.stream().sorted(Comparator.comparing(UserUpload::getCreateTime).reversed())
                .collect(Collectors.toList());
        return new Result(Result.RESULT_OK,list,new Date().getTime());
    }

}

