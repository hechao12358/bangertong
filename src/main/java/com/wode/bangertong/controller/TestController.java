package com.wode.bangertong.controller;

import com.wode.bangertong.common.entity.UserAudio;
import com.wode.bangertong.mapper.UserAudioMapper;
import com.wode.bangertong.service.BannerSercive;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮播图接口
 *
 * @author hc
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    UserAudioMapper userAudioMapper;

    @RequestMapping("/test")
    public Object getBannerList(){
        UserAudio userAudio = new UserAudio();
//        userAudio.setUserId("123");
        Map<String, Object> map = new HashMap<>();
        map.put("user_id","1813510577846558721");
        map.put("id","1813510577846558722");

        List<UserAudio> userAudios = userAudioMapper.selectByMap(map);
        return  userAudios;
    }
}
