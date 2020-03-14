package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.dto.FileUploadDTO;
import com.travel.community.travel_demo.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileUploadController {

    @Autowired
    private UCloudProvider uCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileUploadDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String uploadFileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileUploadDTO fileUploadDTO = new FileUploadDTO();
            fileUploadDTO.setSuccess(1);
            fileUploadDTO.setUrl(uploadFileName);
            return fileUploadDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setSuccess(1);
        fileUploadDTO.setUrl("/images/gala.jpg");
//        fileUploadDTO.setMessage();
        return fileUploadDTO;
    }
}
