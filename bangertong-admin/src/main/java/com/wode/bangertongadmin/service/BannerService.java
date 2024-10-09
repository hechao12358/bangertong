package com.wode.bangertongadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.Banner;
import com.wode.bangertong.common.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface BannerService {

    Result getPage(Page page, Banner banner);

    Result insertBanner(Banner banner);

    Result updateBanner(Banner banner);

    Result delBanner(String id);

}
