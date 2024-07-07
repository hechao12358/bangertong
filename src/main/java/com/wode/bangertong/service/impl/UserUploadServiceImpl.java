package com.wode.bangertong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wode.bangertong.entity.UserUpload;
import com.wode.bangertong.mapper.UserUploadDao;
import com.wode.bangertong.service.UserUploadService;
import org.springframework.stereotype.Service;

/**
 * 用户下载表(UserUpload)表服务实现类
 *
 * @author makejava
 * @since 2024-07-06 17:50:46
 */
@Service("userUploadService")
public class UserUploadServiceImpl extends ServiceImpl<UserUploadDao, UserUpload> implements UserUploadService {

}

