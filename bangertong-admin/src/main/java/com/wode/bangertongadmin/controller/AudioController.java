package com.wode.bangertongadmin.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.mysql.jdbc.StringUtils;
import com.wode.bangertong.common.dto.AudioDTO;
import com.wode.bangertong.common.dto.AudioTypeDto;
import com.wode.bangertong.common.entity.Audio;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.service.AudioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 音乐表(Audio)表控制层
 *
 * @author makejava
 * @since 2024-07-05 17:02:11
 */
@RestController
@RequestMapping("/bangertong-admin/audio/api")
@Slf4j
public class AudioController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private AudioService audioService;


    /**
     * 查询各分类的音乐
     * @param dto
     * @return
     */
    @PostMapping("/selectMusicByType")
    public Result selectMusicByType(@RequestBody AudioTypeDto dto){
        if (null == dto || StringUtils.isNullOrEmpty(dto.getType())) {
            return new Result(Result.VERIFY_CODE_TIME,"请输入音乐类型",new Date().getTime());
        }
        List<Audio> list = audioService.selectMusicByType(dto);
        return new Result(Result.RESULT_OK,list,new Date().getTime());
    }

    /**
     * 查询各分类的音乐
     * @param type
     * @return
     */
    @GetMapping("/selectAudioByType")
    public Result selectAudioByType(@RequestHeader String Authorization, String type){
        if (null == type || StringUtils.isNullOrEmpty(type)) {
            return new Result(Result.VERIFY_CODE_TIME,"请输入音乐类型",new Date().getTime());
        }
        return audioService.selectAudioByType(Authorization,type);
    }

    /**
     * 查询全部音乐（进内部人员使用）
     * @return
     */
    @GetMapping("/selectMusicAll")
    public Result selectMusicAllByUserId(String userId){
        return audioService.selectMusicAllByUserId(userId);
    }

    /**
     * 更新音乐状态
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody AudioDTO audioDTO){
        return audioService.updateAudioStatus(audioDTO);
    }

}

