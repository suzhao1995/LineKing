package com.rs.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

/**
* Pdf2ImageUtil.java
* @Description:PDF转换为图片流
* @author: suzhao
* @date: 2019年8月5日 下午10:12:26
* @version: V1.0
*/
@Component
public class Pdf2ImageUtil{
	private static final Logger logger = Logger.getLogger(Pdf2ImageUtil.class);
	
	private static String filePath;	//文件路径
	
	@Value("${filePath}")	//静态属性使用setter方法注入properties文件的属性
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	 /**
     * 转换全部的pdf
     * @param fileAddress 文件地址
     * @param filename PDF文件名
     * @param type 图片类型
	 * @throws IOException 
     */
    public static Map<String,Object> pdf2png(HttpServletRequest request,String fileAddress,String filename,String type) throws Exception {
    	//文件保存路径
    	//String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");	//保存的文件根目录
    	String savePath = filePath;
    	// 将pdf装图片 并且自定义图片得格式大小
        File file = new File(savePath +"\\"+fileAddress+"\\"+filename+".pdf");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<String> list = new ArrayList<String>();
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            resultMap.put("pageCount", pageCount);	//总页数
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
               
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
                ImageIO.write(image, type, baos);
                byte[] bytes = baos.toByteArray();//转换成字节
                BASE64Encoder encoder = new BASE64Encoder();
                String png_base64 = encoder.encodeBuffer(bytes).trim();
                png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
                png_base64 = "data:image/png;base64,"+png_base64;
                list.add(png_base64);
            }
            resultMap.put("imgList", list);
            resultMap.put("code", "0");
            resultMap.put("message", "转换成功");
        } catch (IOException e) {
            logger.error("------转换异常------", e);
            resultMap.put("code", "-1");
            resultMap.put("message", "系统异常");
        }finally {
	        if (doc != null) {
	            doc.close();
	        }
        }
        return resultMap;
    }
 
}