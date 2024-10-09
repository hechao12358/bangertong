package com.wode.bangertong.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wode.bangertong.common.entity.HtmlPage;
import com.wode.bangertong.mapper.HtmlPageMapper;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.HtmlPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class HtmlPageServiceImpl implements HtmlPageService {
    @Resource
    HtmlPageMapper htmlPageMapper;

    @Override
    public Result getHtmlPageList(String classfiy, Integer parentId) {
        HtmlPage htmlPage = new HtmlPage();
        htmlPage.setClassify(classfiy);
        htmlPage.setStatus(1);
        if(null != parentId){
            htmlPage.setParentId(parentId);
        }
        List<HtmlPage> htmlPages = htmlPageMapper.selectList(Wrappers.query(htmlPage).orderByDesc("order_num"));

        return new Result(Result.RESULT_OK, htmlPages, new Date().getTime());
    }
}
