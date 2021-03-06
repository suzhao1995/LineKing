package com.rs.common.utils;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.controller.backstage.SectionController;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * @author 汪航
 * @Description 删除文件工具类
 * @create 2019-08-17 12:51
 */
public class DeleteFileUtil {


    private static final Logger logger = Logger.getLogger(DeleteFileUtil.class);

    /**
     * 循环删除文件传入数组
     * @param fileNames
     */
    public static void deleteFiles(String[] fileNames){
        for (int i = 0; i < fileNames.length ; i++) {
            deleteFile(fileNames[i]);
        }
    }

    /**
     * 循环删除文件传入数组
     * @param fileNames
     */
    public static void deleteFiles(List<String> fileNames){
        for (int i = 0; i < fileNames.size() ; i++) {
            deleteFile(fileNames.get(i));
        }
    }

    /**
     * 删除单个文件
     * @param fileName
     * @return
     */
    public static void deleteFile(String fileName){
        if (StrUtil.isNotBlank(fileName)){
            File file = new File(fileName);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除&& file.isFile()
            if (file.exists()) {
                if (file.delete()) {
                    logger.info("删除单个文件" + fileName + "成功！");
                } else {
                    logger.error("删除单个文件" + fileName + "失败！");
                }
            } else {
                logger.error("删除单个文件失败" + fileName + "不存在！");
            }
        }else {
            logger.error("删除单个文件失败-文件为空！");
        }
    }


    /**
     * 递归删除文件
     * @param
     * @throws
     * @return void
     * @author suzhao
     * @date 2019年8月13日 下午6:23:18
     */
    public static void deleteDir(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file1 : files){
                deleteDir(file1);
            }
            file.delete();
        }else{
            file.delete();
        }
    }
}
