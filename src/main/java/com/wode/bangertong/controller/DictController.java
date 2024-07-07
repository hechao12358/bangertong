package com.wode.bangertong.controller;

import com.wode.bangertong.entity.Dicts;
import com.wode.bangertong.model.Result;
import com.wode.bangertong.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author：lilulu
 * @Date：2024/7/5 21:34
 * @Filename：DIctController
 * @Description：
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;;

    /**
     * 查询音乐分类
     * @return
     */
    @GetMapping("/selectMusic")
    public Result selectMusic(){
        List<Dicts> dictsList = dictService.selectMusic();
        return new Result(Result.RESULT_OK,dictsList,new Date().getTime());
    }

}
