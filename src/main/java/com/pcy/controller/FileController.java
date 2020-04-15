package com.pcy.controller;

import com.pcy.bean.FastDFSFile;
import com.pcy.bean.FileURL;
import com.pcy.bean.ResultObj;
import com.pcy.bean.StatusCode;
import com.pcy.util.FastDFSClient;
import org.csource.fastdfs.FileInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

@RestController
@CrossOrigin
public class FileController {

    public static final String STORAGE_SERVER_PORT = "8888";


    /**
     * 获取文件信息
     * @param map
     * @return
     */
    @PostMapping(value = "/info")
    public FileInfo getFileInfo(@RequestBody Map<String, String> map){
        String groupName = map.get("groupName");
        String remoteFileName = map.get("remoteFileName");
        FileInfo fileInfo = FastDFSClient.getFile(groupName, remoteFileName);
        return fileInfo;
    }

    /***
     * 文件上传
     * @return
     */
    @PostMapping(value = "/upload")
    public ResultObj<FileURL> upload(@RequestParam("file") MultipartFile file) throws Exception {
        //封装一个FastDFSFile
        FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(), file.getBytes(), StringUtils.getFilenameExtension(file.getOriginalFilename()));

        //文件上传
        String[] uploads = FastDFSClient.upload(fastDFSFile);
        //组装文件上传地址
        String groupName = uploads[0];
        String remoteFileName = uploads[1];
        FileInfo fileInfo = FastDFSClient.getFile(groupName, remoteFileName);
        if (fileInfo.getFileSize() == 0){
            FastDFSClient.deleteFile(groupName, remoteFileName);
            return new ResultObj<FileURL>(false, StatusCode.ERROR, "上传失败", null);
        }else {
            String split = remoteFileName.substring(uploads[1].indexOf("/"));
            String prefix = FastDFSClient.getTrackerUrl().substring(0, FastDFSClient.getTrackerUrl().lastIndexOf(":"));
            String visitUrl = prefix + ":" + STORAGE_SERVER_PORT + split;
            String realUrl = FastDFSClient.getTrackerUrl()+"/"+ groupName +"/"+ remoteFileName;
            FileURL fileURL = new FileURL(groupName, remoteFileName, realUrl, visitUrl);
            return new ResultObj<FileURL>(true, StatusCode.OK, "上传成功", fileURL);
        }
    }

    /**
     * 删除文件
     * @param map
     * @return
     */
    @DeleteMapping(value = "/delete")
    public ResultObj delete(@RequestBody Map<String, String> map){
        String groupName = map.get("groupName");
        String remoteFileName = map.get("remoteFileName");
        FastDFSClient.deleteFile(groupName, remoteFileName);
        return new ResultObj(true, StatusCode.OK, "删除成功",null);
    }


}