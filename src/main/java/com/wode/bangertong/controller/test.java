package com.wode.bangertong.controller;

import com.wode.bangertong.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/banertong/test/api")
public class test {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Result test(){
        return new Result(Result.RESULT_OK, "ok", new Date().getTime());
    }

}
