package com.lnmj.common.baseController;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.FtpFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/5/10 11:18
 * @Description:
 */
/**
 * Created by nishuai on 2017/12/26.
 */
@RestController
@RequestMapping("/image")
public class FtpFileUploadController {
    @Resource
    FtpFileUtil ftpFileUtil;

    //ftp处理文件上传
    @RequestMapping(value="/ftpuploadImg", method = RequestMethod.POST)
    public ResponseResult uploadImg(@RequestParam("file") MultipartFile file, String imageType) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream=file.getInputStream();
        String filePath=null;
        Map resultMap = ftpFileUtil.uploadFile(fileName,inputStream,imageType);
        Boolean flag= (Boolean)resultMap.get("flag");
        if(flag==true){
            System.out.println("ftp上传成功！");
            filePath=(String)resultMap.get("url");
            JSONObject imgObject = new JSONObject();
            imgObject.put("imageType",imageType);
            imgObject.put("imageUrl",filePath);
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image != null) {//如果image=null 表示上传的不是图片格式
                imgObject.put("imageWidth", image.getWidth());
                imgObject.put("imageHeight", image.getHeight());
            }else{
                ResponseResult.error(new Error(1,"不是图片格式"));
            }
            return ResponseResult.success(imgObject);
        }else{
            return ResponseResult.error(new Error(1,resultMap.get("msg").toString()));
        }
    }

    //ftp处理文件删除
    @RequestMapping(value="/deleteImg", method = RequestMethod.POST)
    public ResponseResult deleteImg(@RequestParam("fileName") String fileName,String imageType) throws IOException {
            if (StringUtils.isBlank(fileName)){
              return   ResponseResult.error(new Error(1,"文件名不能为空"));
            }
        Map resultMap =  ftpFileUtil.deleteFile(fileName,imageType);
        Boolean flag= (Boolean)resultMap.get("flag");
        if(flag==true){
            System.out.println("删除成功！");
            return ResponseResult.success();
        }else{
            return ResponseResult.error(new Error(1,resultMap.get("msg").toString()));
        }


    }

}
