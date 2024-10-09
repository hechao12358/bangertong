package com.wode.bangertong.controller;

import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.HtmlPageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bangertong/htmlPage")
public class HtmlPageController {

    @Resource
    HtmlPageService htmlPageService;

    @RequestMapping("/list")
    public Result getBannerList(String classfiy, Integer parentId){
        return htmlPageService.getHtmlPageList(classfiy, parentId);
    }
}
