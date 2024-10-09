package com.wode.bangertongadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.dto.UserDTO;
import com.wode.bangertong.common.entity.User;
import org.springframework.stereotype.Service;
import com.wode.bangertong.common.model.Result;

@Service
public interface UserService {

    Result getPage(Page page, UserDTO userDTO);
}
