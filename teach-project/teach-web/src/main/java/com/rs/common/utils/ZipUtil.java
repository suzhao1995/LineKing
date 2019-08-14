package com.rs.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
* ZipUtil.java
* @Description:压缩工具类
* @author: suzhao
* @date: 2019年8月13日 上午11:37:13
* @version: V1.0
*/
public class ZipUtil{
	private static final Logger logger = Logger.getLogger(ZipUtil.class);
	/**
	* 压缩列表中的文件
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年8月13日 上午11:39:05
	*/
	public static void zipFile(List<File> files, ZipOutputStream outputStream){
		for(File file : files){
			try {
				
				ZipUtil.zipFile(file, outputStream);
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	/**
	* 将文件写入zip文件中
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年8月13日 上午11:39:48
	*/
	public static void zipFile(File inputFile, ZipOutputStream outputstream){
		try {
			if(inputFile.exists() && inputFile.isFile()){
				FileInputStream inStream = new FileInputStream(inputFile);
                BufferedInputStream bInStream = new BufferedInputStream(inStream);
                ZipEntry entry = new ZipEntry(inputFile.getName());
                outputstream.putNextEntry(entry);
                
                final int MAX_BYTE = 20 * 1024 * 1024;    //最大的流为20M
                long streamTotal = 0;                      //接受流的容量
                int streamNum = 0;                      //流需要分开的数量
                int leaveByte = 0;                      //文件剩下的字符数
                byte[] inOutbyte;                          //byte数组接受文件的数据
                
                streamTotal = bInStream.available();                        //通过available方法取得流的最大字符数
                streamNum = (int) Math.floor(streamTotal / MAX_BYTE);    //取得流文件需要分开的数量
                leaveByte = (int) streamTotal % MAX_BYTE;                //分开文件之后,剩余的数量
                
                if (streamNum > 0) {
                    for (int j = 0; j < streamNum; ++j) {
                        inOutbyte = new byte[MAX_BYTE];
                        //读入流,保存在byte数组
                        bInStream.read(inOutbyte, 0, MAX_BYTE);
                        outputstream.write(inOutbyte, 0, MAX_BYTE);  //写出流
                    }
                }
                //写出剩下的流数据
                inOutbyte = new byte[leaveByte];
                bInStream.read(inOutbyte, 0, leaveByte);
                outputstream.write(inOutbyte);
                outputstream.closeEntry();    
                bInStream.close();    //关闭
                inStream.close();
			}else{
				throw new FileNotFoundException("文件不存在");
			}
		} catch (Exception e) {
			logger.error("-------压缩异常-------", e);
		}
	}
	
	/**
	* 下载打包好的zip文件
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年8月13日 上午11:57:16
	*/
	public static void downloadZip(File file, HttpServletResponse response, String downloadName) {
	    try {
	        // 以流的形式下载文件。
	        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	        // 清空response
	        response.reset();

	        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadName+".zip", "UTF-8"));
	        toClient.write(buffer);
	        toClient.flush();
	        toClient.close();
	        file.delete();        //将生成的服务器端文件删除
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}

}