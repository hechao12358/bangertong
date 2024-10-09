package com.wode.bangertongadmin.controller;

import com.wode.bangertong.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/bangertong-admin/fileUpload/api")
public class FileController {

    /**
     * 上传图片
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public Result imgUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = "";  // 后缀名

        if(fileName.equals("blob")){
            suffixName = ".jpg";  // 后缀名
        }else {
            suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        }
        String filePath = "/home/hechao/apache-tomcat-8.5.83/webapps/bangertong-assets/images/"; // 上传后的路径
//        String filePath = "D:\\"; // 上传后的路径

        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(Result.RESULT_OK, "https://warminggaoke.com/bangertong-assets/images/" + fileName, new Date().getTime());
    }

    /**
     * 上传视频
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/videoUpload", method = RequestMethod.POST)
    public Result upload(@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {

        if (file.isEmpty()) {
            System.out.println("文件为空");
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "/home/hechao/apache-tomcat-8.5.83/webapps/bangertong-assets/videos/"; // 上传后的路径

        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(Result.RESULT_OK, "https://warminggaoke.com/bangertong-assets/videos/" + fileName, new Date().getTime());
    }
}
