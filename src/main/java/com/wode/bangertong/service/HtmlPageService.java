package com.wode.bangertong.service;

import com.wode.bangertong.common.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface HtmlPageService {
    Result getHtmlPageList(String classfiy,Integer parentId);
}
