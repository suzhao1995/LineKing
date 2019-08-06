package com.rs.common.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
* Office2PdfUtil.java
* @Description:office转换为pdf
* @author: suzhao
* @date: 2019年8月5日 下午10:11:22
* @version: V1.0
*/
public class Office2PdfUtil{
	private static final Logger logger = Logger.getLogger(Office2PdfUtil.class);
	
	// 将word格式的文件转换为pdf格式
    public static Map<String,Object> Word2Pdf(String srcPath, String desPath) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
        OpenOfficeConnection connection = null;
        Process p = null;
        try {
            // 源文件目录
            File inputFile = new File(srcPath);
            if (!inputFile.exists()) {
                resultMap.put("resultCode", "-1");
                resultMap.put("message", "源文件不存在");
                return resultMap;
            }
            // 输出文件目录
            File outputFile = new File(desPath);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().exists();
            }
            // 调用openoffice服务线程
            String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
            p = Runtime.getRuntime().exec(command);
 
            // 连接openoffice服务
            connection = new SocketOpenOfficeConnection(
                    "127.0.0.1", 8100);
            connection.connect();
 
            // 转换word到pdf
            DocumentConverter converter = new OpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);
 
            logger.info("------文件转换完成------");
        } catch (Exception e) {
        	 resultMap.put("resultCode", "-1");
             resultMap.put("message", "文件上传异常");
        } finally {
            if (connection != null) {
                // 关闭连接
                connection.disconnect();
            }
            if (p != null) {
                // 关闭进程
                p.destroy();
            }
        }
        return resultMap;
    }
}