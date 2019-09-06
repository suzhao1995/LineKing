package com.rs.teach.controller.backstage;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DateUtil;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.common.utils.ZipUtil;
import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.sysCode.entity.SysCode;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.mapper.video.entity.VideoSectionVo;
import com.rs.teach.service.studyAttr.TestAndWorkService;
import com.rs.teach.service.sysCode.SysCodeService;
import com.rs.teach.service.video.VideoService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author 汪航
 * @Description  管理员 -- video模块
 * @create 2019-08-21 16:47
 */
@Controller
@RequestMapping("/beforeVideo")
public class BeforeVideoController {
	private Logger logger  = Logger.getLogger(BeforeVideoController.class);
	
    @Autowired
    private VideoService videoService;
    
    @Autowired
	private SysCodeService sysCodeService;
    
    @Autowired
	private TestAndWorkService testAndWorkService;
    
    /**
    * 下载单个视频课件
    * @param 
    * @throws
    * @return ResponseBean
    * @author suzhao
    * @date 2019年9月2日 下午3:43:24
    */
    @RequestMapping("/download")
    @ResponseBody
    public ResponseBean download(HttpServletRequest request, HttpServletResponse response ){
        ResponseBean bean = new ResponseBean();
        String videoSectionId = request.getParameter("videoSectionId");
        try {
            VideoSection videoSection = videoService.getSectionBySecId(videoSectionId);
            //下载视频文件
            Map<String, Object> resultMap = FileUpDownUtil.videoFileDownLoad(response, videoSection.getVideoSectionUrl(), videoSection.getVideoSectionName());
            if(resultMap != null && "0".equals(resultMap.get("code"))){
            	bean.addSuccess("下载成功");
            }else{
            	bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "下载失败");
            }
        } catch (IOException e) {
            logger.error("视频下载异常", e);
            bean.addError("资源下载异常");
        }
        return bean;
    }

    
    /**
    * 下载视频课程所有信息
    * @param 
    * @throws
    * @return ResponseBean
    * @author suzhao
    * @date 2019年9月2日 下午3:45:58
    */
    @RequestMapping("/videoAllDownload")
    @ResponseBody
    public ResponseBean videoAllDownload(HttpServletRequest request, HttpServletResponse response ){
        ResponseBean bean = new ResponseBean();
        String videoId = request.getParameter("videoId");
        Video video = videoService.getVideoById(videoId);
        String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        
        List<Section> sections = videoService.queryVideoSectionByVideoId(videoId);
        //服务器端创建文件临时储存总目录
        String temporaryPath = request.getSession().getServletContext().getRealPath("/WEB-INF/allDownLoad/" + userId + videoId);
        try {
			
        	FileUpDownUtil.allDownLoadUtil(sections, temporaryPath);
        	//压缩总目录
        	File zipFile = cn.hutool.core.util.ZipUtil.zip(temporaryPath, temporaryPath + ".zip", true);
        	ZipUtil.downloadZip(zipFile, response, video.getVideoName());
        	//删除临时文件根目录
        	File temporaryFile = new File(temporaryPath);
        	DeleteFileUtil.deleteDir(temporaryFile);
        	bean.addSuccess();
		} catch (Exception e) {
			logger.error("全部视频信息下载异常", e);
			bean.addError("全部视频下载异常");
		}
        return bean;
    }
    
    /**
	* 管理员---初始化视频列表
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/initVideo")
	@ResponseBody
	public ResponseBean initVideo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		String videoName = request.getParameter("videoName");	//视频名称
		String videoType = request.getParameter("code");		//视频分类
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		
		//初始化分页信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//查询所有视频信息
		List<Video> list = videoService.adminVideosInit(videoName, videoType);
		PageInfo<Video> info = new PageInfo<Video>(list,9);
		ajaxData.put("videoList", info);
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 管理员---新增视频课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/addVideo")
	@ResponseBody
	public ResponseBean addVideo(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		
		//上传视频封面文件
		Map<String,Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
		if(resultMap != null && "0".equals(resultMap.get("code"))){
			Video video = new Video();
			video.setVideoId(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));
			video.setVideoName(request.getParameter("videoName"));	//视频名称
			video.setVideoType(request.getParameter("code"));		//视频分类
			video.setVideoUrl(resultMap.get("picUrl").toString());//视频封面url
			video.setVideoPath(resultMap.get("saveUrl").toString());	//存在服务器的路径
			video.setSchoolWork(request.getParameter("schoolWork"));	//是否有作业
			video.setTestPaper(request.getParameter("testPaper"));	//是否有考试
			video.setVideoWare(request.getParameter("videoWare"));	//是否有课件
			try {
				videoService.adminAddVideo(video);
				bean.addSuccess();
			} catch (Exception e) {
				//入库失败删除服务器文件
				//删除封面图片
				DeleteFileUtil.deleteFile(video.getVideoPath());
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "上传失败");
			}
		}
		
		return bean;
	}
	
	/**
	* 管理员---删除视频课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月3日 上午11:22:59
	*/
	@RequestMapping("/delVideo")
	@ResponseBody
	public ResponseBean delVideo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String videoId = request.getParameter("videoId");
		if(StringUtils.isEmpty(videoId)){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"请选择一条记录");
			return bean;
		}
		//查询该视频课程是否含有视频课件信息
		List<VideoSection> list = videoService.getVideoSection(videoId);
		if(list.size() > 0){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课程含有课件信息，请先删除课件信息");
			return bean;
		}
		try {
			//查询视频课程信息，删除服务器文件
			Video video = videoService.getVideoById(videoId);
			if(video != null){
				DeleteFileUtil.deleteFile(video.getVideoPath());
				videoService.adminDelVideo(videoId);
				bean.addSuccess();
			}
		} catch (Exception e) {
			bean.addError("删除视频课程失败");
			logger.info("---删除视频课程失败---", e);
		}
		return bean;
	}
	
	
	/**
	* 管理员---编辑视频课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月3日 上午11:53:58
	*/
	@RequestMapping("/updateVideo")
	@ResponseBody
	public ResponseBean updateVideo(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		String videoId = request.getParameter("videoId");
		if(StringUtils.isEmpty(videoId)){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"请选择一条记录");
			return bean;
		}
		
		//根据id查询视频课程信息
		Video modify = videoService.getVideoById(videoId);
		
		//修改后的物料信息
		Video video = new Video();
		video.setVideoId(videoId);
		video.setVideoName(request.getParameter("videoName"));	//视频名称
		video.setVideoType(request.getParameter("code"));	//视频分类
		video.setSchoolWork(request.getParameter("schoolWork"));	//是否有联系
		video.setTestPaper(request.getParameter("testPaper"));	//是否有考试
		video.setVideoWare(request.getParameter("videoWare"));	//是否有课件
		
		if(StringUtils.isNotEmpty(file.getOriginalFilename())){
			//上传视频封面文件
			Map<String,Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
			if(resultMap != null && "0".equals(resultMap.get("code"))){
				video.setVideoUrl(resultMap.get("picUrl").toString());	//视频封面url
				video.setVideoPath(resultMap.get("saveUrl").toString());	//存在服务器的路径
			}
		}
		try {
			videoService.adminUpdate(video);
			if(StringUtils.isNotEmpty(video.getVideoUrl()) && StringUtils.isNotEmpty(video.getVideoPath())){
				//删除服务器原有封面图片
				DeleteFileUtil.deleteFile(modify.getVideoPath());
			}
			bean.addSuccess();
		} catch (Exception e) {
			//入库失败删除服务器文件
			//删除封面图片
			DeleteFileUtil.deleteFile(video.getVideoPath());
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "上传失败");
		}
		
		return bean;
	}
	
	
	/**
	* 管理员---科目分类（视频列表查看按钮）
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月3日 下午12:16:55
	*/
	@RequestMapping("/initVideoSection")
	@ResponseBody
	public ResponseBean initVideoSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		String videoId = request.getParameter("videoId");
		
		//查询视频课件信息
		List<Map<String,Object>> list = videoService.adminGetVideoInfo(videoId);
		if(list.isEmpty()){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "该视频课程展示没有章节信息，请添加");
			return bean; 
		}
		List<VideoSectionVo> videoList = groupByTotle(list);
		ajaxData.put("videoList", videoList);
		bean.addSuccess(ajaxData);
		return bean;
	}
    
	/**
	* 管理员---添加大章节
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月3日 下午2:09:30
	*/
	@RequestMapping("/addTotleSection")
	@ResponseBody
	public ResponseBean addTotleSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String totleName = request.getParameter("totleName");
		String videoId = request.getParameter("videoId");
		//根据videoId查询视频课程的大章节信息
		List<Map<String,String>> totleList = videoService.adminTotleInfo(videoId);
		TotleSection totleSection = new TotleSection();
		totleSection.setTotleSectionName(totleName);
		totleSection.setCourseId(videoId);
		if(totleList.size() == 0){
			totleSection.setTotleSectionSort("1");
		}else{
			Map<String,String> map = totleList.get(totleList.size() - 1);
			int totleSort = Integer.valueOf(map.get("totleSort")) + 1;
			totleSection.setTotleSectionSort(String.valueOf(totleSort));
		}
		try {
			
			videoService.adminAddTotleInfo(totleSection);
		} catch (Exception e) {
			bean.addError("新增失败");
			logger.info("新增大章节失败", e);
		}
		return bean;
	}
	
	/**
	* 管理员---修改大章节
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月3日 下午2:09:30
	*/
	@RequestMapping("/updateTotleSection")
	@ResponseBody
	public ResponseBean updateTotleSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String totleName = request.getParameter("totleName");
		String totleId = request.getParameter("videoTotleId");	//大章节id
		
		TotleSection totleSection = videoService.getTotleSection(totleId);
		totleSection.setTotleSectionName(totleName);
		try {
			videoService.updateTotleSection(totleSection);
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("修改失败");
			logger.info("修改大章节名失败", e);
		}
		
		return bean;
	}
	
	/**
	* 管理员---添加课件信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月3日 下午5:30:20
	*/
	@RequestMapping("/addVideoSection")
	@ResponseBody
	public ResponseBean addVideoSection(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] files){
		ResponseBean bean = new ResponseBean();
		String videoSectionName = request.getParameter("videoSectionName");
		
		if(StringUtils.isEmpty(files[0].getOriginalFilename())){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "请上传指定视频课件");
			return bean;
		}
		VideoSection videoSection = new VideoSection();
		videoSection.setVideoSectionId(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));	//视频课件id
		videoSection.setVideoSectionName(videoSectionName); 	//视频课件名称
		videoSection.setVideoId(request.getParameter("videoId"));	//视频课程id
		videoSection.setVideoTotleSortId(request.getParameter("videoTotleId"));	//大章节id
		//查询视频课件上传序号
		List<VideoSection> videoSections = videoService.adminGetVedioSection(request.getParameter("videoTotleId"), request.getParameter("videoId"));
		if(videoSections.size() > 0){
			int sort = Integer.valueOf(videoSections.get(videoSections.size() - 1).getVideoSectionSort()) + 1;
			videoSection.setVideoSectionSort(String.valueOf(sort));
		}else{
			videoSection.setVideoSectionSort("1");
		}
		//上传视频
		if(StringUtils.isNotEmpty(files[0].getOriginalFilename())){
			MultipartFile videoSectionFile = files[0];
			Map<String,Object> resultMap = FileUpDownUtil.videoUpload(videoSectionFile);
			if(resultMap != null && "0".equals(resultMap.get("code"))){
				videoSection.setVideoSectionUrl(resultMap.get("videoUrl").toString());
				videoSection.setVideoSectionPath(resultMap.get("videoPath").toString());
			}else{
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课件上传失败！请重试");
				return bean;
			}
		}
		//上传作业
		Practice work = new Practice();
		if(StringUtils.isNotEmpty(files[1].getOriginalFilename())){
			MultipartFile workFile = files[1];
			Map<String,Object> workMap = FileUpDownUtil.fileUpLoad(request, workFile);
			if(workMap != null && "0".equals(workMap.get("code"))){
				videoSection.setWorkId(workMap.get("upLoadId").toString());
				//插入作业表
				work.setPracticeId(workMap.get("upLoadId").toString());
				work.setPracticeFileName(workMap.get("updateFileName").toString());
				work.setPracticePath(workMap.get("fileMappingPath").toString());
				work.setPracticeUrl(workMap.get("pdfUrl").toString());
			}else{
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课件上传失败！请重试");
				return bean;
			}
		}
		
		Testpaper test = new Testpaper();
		//上传考试文件
		if(StringUtils.isNotEmpty(files[2].getOriginalFilename())){
			MultipartFile paperFile = files[2];
			Map<String,Object> paperMap = FileUpDownUtil.fileUpLoad(request, paperFile);
			if(paperMap != null && "0".equals(paperMap.get("code"))){
				videoSection.setPaperId(paperMap.get("upLoadId").toString());
	
				//插入试卷表
				test.setTestpaperId(paperMap.get("upLoadId").toString());
				test.setTestpaperName(paperMap.get("updateFileName").toString());
				test.setTestpaperPath(paperMap.get("fileMappingPath").toString());
				test.setTestpaperUrl(paperMap.get("pdfUrl").toString());
			}else{
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课件上传失败！请重试");
				return bean;
			}
		}
		
		try {
			videoService.insertVideoSection(videoSection,work,test);
			bean.addSuccess();
		} catch (Exception e) {
			logger.error("-----视频模块视频上传入表失败----", e);
			//删除原有课件
			String[] filePath = new String[5];
			filePath[0] = videoSection.getVideoSectionUrl();	//删除视频文件
			if(test != null){
				filePath[1] = test.getTestpaperUrl();	//删除试卷文件
				filePath[2] = test.getTestpaperUrl().split("[.]")[0] + ".ppt";	//删除试卷文件的原始文件
			}
			if(work != null){
				filePath[3] = work.getPracticeUrl();	//删除作业文件
				filePath[4] = work.getPracticeUrl().split("[.]")[0] + ".ppt";	//删除作业文件的原始文件
			}
			DeleteFileUtil.deleteFiles(filePath);
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "上传失败！");
		}
		
		return bean;
	}
	
	/**
	* 管理员---删除小章节
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月4日 下午3:26:52
	*/
	@RequestMapping("/delVideoSection")
	@ResponseBody
	public ResponseBean delVideoSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String videoSectionId = request.getParameter("videoSectionId");
		//查询小章节信息
		VideoSection videoSection = videoService.getSectionBySecId(videoSectionId);
		if(videoSection == null){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "小章节不存在！");
			return bean;
		}
		//查询章节绑定的作业
		Practice work = testAndWorkService.getPracticeById(videoSection.getWorkId());
		if(work != null){
			DeleteFileUtil.deleteFile(work.getPracticeUrl()); 	//删除pdf文件
			String pptUrl = work.getPracticeUrl().split("[.]")[0] + ".ppt";
			DeleteFileUtil.deleteFile(pptUrl);
			testAndWorkService.delWork(work.getPid());
		}
		//查询章节绑定的试卷
		Testpaper test = testAndWorkService.getTestpaper(videoSection.getPaperId());
		if(test != null){
			//删除原有课件
			DeleteFileUtil.deleteFile(test.getTestpaperUrl());	//删除pdf文件
			String pptUrl = test.getTestpaperUrl().split("[.]")[0] + ".ppt";
			DeleteFileUtil.deleteFile(pptUrl);
			testAndWorkService.delTestPaper(test.getTid());
		}
		//删除小章节
		DeleteFileUtil.deleteFile(videoSection.getVideoSectionUrl());
		videoService.delVideoSection(videoSectionId);
		bean.addSuccess();
		return bean;
	}
	
	/**
	* 管理员---编辑小章节
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月4日 下午3:48:01
	*/
	@RequestMapping("/updateVideoSection")
	@ResponseBody
	public ResponseBean updateVideoSection(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] files){
		ResponseBean bean = new ResponseBean();
		String videoSectionId = request.getParameter("videoSectionId");
		
		//查询小章节信息
		VideoSection modify = videoService.getSectionBySecId(videoSectionId);
		if(modify == null){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "小章节不存在！");
			return bean;
		}
		VideoSection videoSection = new VideoSection();
		videoSection.setVideoSectionId(videoSectionId); 	//章节id
		videoSection.setVideoSectionName(request.getParameter("videoSectionName")); 	//章节名称
		videoSection.setVideoTotleSortId(modify.getVideoTotleSortId()); 	//大章节序号id
		videoSection.setVideoId(modify.getVideoId());	//视频课程id
		videoSection.setVideoSectionSort(modify.getVideoSectionSort());	//视频章节序号
		//上传视频
		if(StringUtils.isNotEmpty(files[0].getOriginalFilename())){
			MultipartFile videoSectionFile = files[0];
			Map<String,Object> resultMap = FileUpDownUtil.videoUpload(videoSectionFile);
			if(resultMap != null && "0".equals(resultMap.get("code"))){
				videoSection.setVideoSectionUrl(resultMap.get("videoUrl").toString());
				videoSection.setVideoSectionPath(resultMap.get("videoPath").toString());
			}else{
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课件上传失败！请重试");
				return bean;
			}
		}
		//作业
		Practice work = new Practice();
		if(StringUtils.isNotEmpty(files[1].getOriginalFilename())){
			MultipartFile workFile = files[1];
			Map<String,Object> workMap = FileUpDownUtil.fileUpLoad(request, workFile);
			if(workMap != null && "0".equals(workMap.get("code"))){
				videoSection.setWorkId(workMap.get("upLoadId").toString());
				//插入作业表
				work.setPracticeId(workMap.get("upLoadId").toString());
				work.setPracticeFileName(workMap.get("updateFileName").toString());
				work.setPracticePath(workMap.get("fileMappingPath").toString());
				work.setPracticeUrl(workMap.get("pdfUrl").toString());
			}else{
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课件上传失败！请重试");
				return bean;
			}
		}
		
		Testpaper test = new Testpaper();
		//上传考试文件
		if(StringUtils.isNotEmpty(files[2].getOriginalFilename())){
			MultipartFile paperFile = files[2];
			Map<String,Object> paperMap = FileUpDownUtil.fileUpLoad(request, paperFile);
			if(paperMap != null && "0".equals(paperMap.get("code"))){
				videoSection.setPaperId(paperMap.get("upLoadId").toString());
	
				//插入试卷表
				test.setTestpaperId(paperMap.get("upLoadId").toString());
				test.setTestpaperName(paperMap.get("updateFileName").toString());
				test.setTestpaperPath(paperMap.get("fileMappingPath").toString());
				test.setTestpaperUrl(paperMap.get("pdfUrl").toString());
			}else{
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "视频课件上传失败！请重试");
				return bean;
			}
		}
		try {
			videoService.updateVideoSection(modify,videoSection,work,test);
			if(StringUtils.isNotEmpty(videoSection.getVideoSectionUrl()) && StringUtils.isNotEmpty(videoSection.getVideoSectionPath())){
				//删除原有视频课件信息
				DeleteFileUtil.deleteFile(modify.getVideoSectionUrl());
			}
			if(StringUtils.isNotEmpty(videoSection.getWorkId())){
				//删除原有作业
				Practice p = testAndWorkService.getPracticeById(modify.getWorkId());
				if(p != null){
					DeleteFileUtil.deleteFile(p.getPracticeUrl());
					String pptFile = p.getPracticeUrl().split("[.]")[0] + ".ppt";
					DeleteFileUtil.deleteFile(pptFile);
				}
			}
			if(StringUtils.isNotEmpty(videoSection.getPaperId())){
				//删除原有试卷信息
				Testpaper t = testAndWorkService.getTestpaper(modify.getPaperId());
				if(t != null){
					DeleteFileUtil.deleteFile(t.getTestpaperUrl());
					String pptFile = t.getTestpaperUrl().split("[.]")[0] + ".ppt";
					DeleteFileUtil.deleteFile(pptFile);
				}
			}
			bean.addSuccess();
		} catch (Exception e) {
			logger.error("-----视频模块视频上传入表失败----", e);
			//删除原有课件
			String[] filePath = new String[5];
			filePath[0] = videoSection.getVideoSectionUrl();	//删除视频文件
			if(test != null){
				filePath[1] = test.getTestpaperUrl();	//删除试卷文件
				filePath[2] = test.getTestpaperUrl().split("[.]")[0] + ".ppt";	//删除试卷文件的原始文件
			}
			if(work != null){
				filePath[3] = work.getPracticeUrl();	//删除作业文件
				filePath[4] = work.getPracticeUrl().split("[.]")[0] + ".ppt";	//删除作业文件的原始文件
			}
			DeleteFileUtil.deleteFiles(filePath);
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR, "上传失败！");
		}
		
		return bean;
	}
	
    /**
	* 管理员---初始化视频分类列表
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/initVideoType")
	@ResponseBody
	public ResponseBean initVideoType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		//初始化视频分类
		List<SysCode> list = sysCodeService.getSysCodeList("VIDEO_CODE");
		ajaxData.put("videoType", list);
		bean.addSuccess(ajaxData);
		return bean;
	}
    
    /**
	* 管理员---删除视频分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/delVideoType")
	@ResponseBody
	public ResponseBean delVideoType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String cid = request.getParameter("cid");	//字典主键
		
		if(StringUtils.isEmpty(cid)){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"请选择一条记录");
			return bean;
		}
		//判断该视频分类是否有视频
		SysCode sysCode = sysCodeService.getSysCode(cid);
		if(sysCode != null){
			List<Video> list = videoService.adminGetVideos(sysCode.getCode());
			if(list.size() > 0){
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"该视频分类中含有视频课件，请先删除视频课件");
				return bean;
			}
		}
		//删除物料分类
		sysCodeService.delSysCode(cid);
		bean.addSuccess();
		return bean;
	}
	
	/**
	* 管理员---新增视频分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/addVideoType")
	@ResponseBody
	public ResponseBean addVideoType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String typeName = request.getParameter("typeName");	//分类名称
		//查询物料分类总数 限定9个
		List<SysCode> list = sysCodeService.getSysCodeList("VIDEO_CODE");
		if(list.size() >= 9){
			bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"分类已达上限");
			return bean;
		}
		SysCode sysCode = new SysCode();
		sysCode.setCid(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));
		sysCode.setCodeValue(typeName);
		sysCode.setCodeType("VIDEO_CODE");
		sysCode.setCreateBy(userId);
		sysCode.setCreateDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		if(list.size() > 0){
			SysCode lastCode = list.get(list.size() - 1);	//获取list集合最后一个对象
			int code = Integer.valueOf(lastCode.getCode()) + 1;
			int sort = Integer.valueOf(lastCode.getCodeSort()) + 10;
			sysCode.setCode(String.valueOf(code));
			sysCode.setCodeSort(String.valueOf(sort));
		}else{
			sysCode.setCode("2001");
			sysCode.setCodeSort("1010");
		}
		try {
			int result = sysCodeService.addSysCode(sysCode);
			if(result == 0){
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"新增视频分类失败");
				return bean;
			}
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("系统异常");
			logger.info("---------新增视频分类失败，系统异常--------", e);
		}
		
		return bean;
	}
	
	
	/**
	* 管理员---修改视频分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/updateVideoType")
	@ResponseBody
	public ResponseBean updateVideoType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String typeName = request.getParameter("typeName");	//分类名称
		String cid = request.getParameter("cid");	
		
		SysCode sysCode = new SysCode();
		sysCode.setCid(cid);
		sysCode.setCodeValue(typeName);
		sysCode.setCodeType("VIDEO_CODE");
		sysCode.setCreateBy(userId);
		sysCode.setCreateDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		
		try {
			int result = sysCodeService.modifySysCode(sysCode);
			if(result == 0){
				bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"修改视频分类失败");
				return bean;
			}
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("系统异常");
			logger.info("---------修改视频分类失败，系统异常--------", e);
		}
		
		return bean;
	}
	
	/*
	 * 按大章节目录进行分组
	 * */
	private static List<VideoSectionVo> groupByTotle(List<Map<String,Object>> list){
		List<VideoSectionVo> sectionList = new ArrayList<VideoSectionVo>();
		//按大章节目录进行分组
		Map<String,List<Map<String,Object>>> map = new HashMap<String,List<Map<String,Object>>>();
		for(Map smap : list){
			List<Map<String,Object>> tmpList = map.get(smap.get("totleSectionSort"));
			if(tmpList == null){
				tmpList = new ArrayList<Map<String,Object>>();
				tmpList.add(smap);
				map.put(String.valueOf(smap.get("totleSectionSort")), tmpList);
			}else{
				tmpList.add(smap);
			}
		}
		Set set = map.entrySet();
		Iterator<Set> iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, List<Map<String,Object>>> entry = (Entry<String, List<Map<String,Object>>>) iterator.next();
			VideoSectionVo sectionVo = new VideoSectionVo();
			sectionVo.setVideoTotleSort(entry.getKey());
			sectionVo.setVideoTotleId(String.valueOf(entry.getValue().get(0).get("totleSortId")));
			sectionVo.setVideoTotleName(String.valueOf(entry.getValue().get(0).get("totleSectionName")));
			if(!"null".equals(String.valueOf(entry.getValue().get(0).get("sectionId"))) && StringUtils.isNotEmpty(String.valueOf(entry.getValue().get(0).get("sectionId")))){
				sectionVo.setVideoSectionList(entry.getValue());
			}
			sectionList.add(sectionVo);
		}
		return sectionList;
	}

}
