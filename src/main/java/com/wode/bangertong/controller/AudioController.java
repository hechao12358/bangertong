package com.wode.bangertong.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.mysql.jdbc.StringUtils;
import com.wode.bangertong.dto.AudioTypeDto;
import com.wode.bangertong.entity.Audio;
import com.wode.bangertong.entity.UserUpload;
import com.wode.bangertong.model.Result;
import com.wode.bangertong.service.AudioService;
import com.wode.bangertong.service.UserUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 音乐表(Audio)表控制层
 *
 * @author makejava
 * @since 2024-07-05 17:02:11
 */
@RestController
@RequestMapping("/audio")
@Slf4j
public class AudioController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private AudioService audioService;

    @Autowired
    private UserUploadService userUploadService;


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
     * 下载音乐
     * @param id
     * @return
     */
    @GetMapping("/uploadMusic")
    public void uploadMusic(@Validated Integer id, HttpServletRequest request, HttpServletResponse response){
        if(null == id) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Audio audio = audioService.getById(id);

        if(null == audio || null == audio.getUrl()) {
            log.info("未查询到此音乐");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String url = audio.getUrl();
        int index = url.lastIndexOf("/");
        String fileName = url.substring(index+1);

        //记录到下载表
        UserUpload userUpload = new UserUpload();
        userUpload.setIntroduction(audio.getIntroduction());
        userUpload.setMusicName(audio.getName());
        userUpload.setMusicUrl(audio.getUrl());
        userUpload.setAuthor(audio.getAuthor());
        int index1 = fileName.lastIndexOf(".");
        if (index1 != -1) {
            userUpload.setFileType(fileName.substring(index1+1));
        }
        userUpload.setUserId(request.getHeader("token"));
        userUpload.setIntroduction(audio.getIntroduction());
        userUpload.setIsSucceed(0);
        userUpload.setCreateBy("system");
        userUpload.setUpdateBy("system");
        userUpload.setCreateTime(new Date());
        userUpload.setUpdateTime(new Date());
        userUploadService.save(userUpload);

        //下载音乐
        response.reset(); // 非常重要
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            log.info("用户开始下载音乐，音乐url：{}", url);
            File file = new File(url);
            if (!file.exists()) {
                log.info("无该路径文件：{}",url);
                //下载失败修改下载表状态为失败状态
                userUpload.setIsSucceed(1);
                userUploadService.saveOrUpdate(userUpload);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            // 写出文件流到响应输出流
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            bis.close();
            fis.close();
            log.info("用户下载音乐成功，音乐url：{}", url);
        } catch (Exception e) {
            log.info("音乐文件下载失败：原因：{}",e.getMessage());
            //下载失败修改下载表状态为失败状态
            userUpload.setIsSucceed(1);
            userUploadService.saveOrUpdate(userUpload);
        }
    }

}

