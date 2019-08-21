package com.rs.teach.controller.courseController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.common.utils.ZipUtil;
import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.backstage.SchoolService;
import com.rs.teach.service.resourcesAttr.PicAttrService;
import com.rs.teach.service.section.SectionService;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.studyAttr.StudyTeamService;
import com.rs.teach.service.studyAttr.TestAndWorkService;
import com.rs.teach.service.training.UserCourseRelaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping(value = "/course")
public class CourseController{
	private static final Logger logger = Logger.getLogger(CourseController.class);
	
	@Autowired
	private PicAttrService picAttrService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 班级
	 * */
	@Autowired
	private StudyTeamService studyTeamService;
	
	@Autowired
	private UserCourseRelaService userCourseRelaService;
	
	@Autowired
	private TestAndWorkService testAndWorkService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Value("${fileMappingPath}")
	private String fileMappingPath;	//文件存放根目录
	/**
	* 课程目录资源初始化
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月9日 下午12:11:33
	*/
	@RequestMapping("/initCourse")
	@ResponseBody
	public ResponseBean initCourse(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		//获取页码值，默认为1
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String courseType = request.getParameter("courseType");	//课程类型
		String schoolId = request.getParameter("schoolId");		//校区id
		String courseLev = request.getParameter("courseLev");	//课程等级
		
		String likeSearch = request.getParameter("lickSearch");	//模糊查找的内容
		
		
		//初始化课程信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//分页查询课程
		List<Course> list = courseService.getCourse(courseType, schoolId, courseLev,likeSearch);
		//查询我的课程
		List<Course> myCourse = courseService.getCourseByUserId(userId);
		
		for(Course course:list){
			//查询每个课程的总章节数
			List<Section> sections = sectionService.getSectionByCourseId(course.getCourseId());
			course.setSectionNumber(String.valueOf(sections.size()));
			MyCourseToList(course,myCourse);
		}
		PageInfo<Course> pageInfo = new PageInfo<Course>(list,9);
		bean.addSuccess(pageInfo);
		return bean;
	}
	
	/**
	* 课程资源模块 查询条件信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月15日 上午11:38:49
	*/
	@RequestMapping("/getQueryCondition")
	@ResponseBody
	public ResponseBean getQueryCondition(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		List<School> schoolList = schoolService.selectSchool();
		ajaxData.put("schoolList", schoolList);
		
		List<Map<String,Object>> courseTypeList = courseService.groupCourseType();
		ajaxData.put("courseTypeList", courseTypeList);
		
		List<Map<String,Object>> courseLevList = courseService.groupCourseLev();
		ajaxData.put("courseLevList", courseLevList);
		
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 初始化单个课程的课件信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 上午10:57:34
	*/
	@RequestMapping("/initSection")
	@ResponseBody
	public ResponseBean initSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		//获取请求参数
		String courseId = request.getParameter("courseId");
		
		Course course = courseService.queryCourseByCourseId(courseId);
		
		List<Section> list = sectionService.getSectionByCourseId(courseId);
		course.setSectionNumber(String.valueOf(list.size()));
		ajaxData.put("course", course);	//课程资源信息
		
		List<TrainSectionVo> sectionList = new ArrayList<TrainSectionVo>();
		//按大章节目录进行分组
		Map<String,List<Section>> map = new HashMap<String,List<Section>>();
		for(Section section : list){
			List<Section> tmpList = map.get(section.getTotleSectionSort());
			if(tmpList == null){
				tmpList = new ArrayList<Section>();
				tmpList.add(section);
				map.put(section.getTotleSectionSort(), tmpList);
			}else{
				tmpList.add(section);
			}
		}
		Set set = map.entrySet();
		Iterator<Set> iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, List<Section>> entry = (Entry<String, List<Section>>) iterator.next();
			TrainSectionVo sectionVo = new TrainSectionVo();
			sectionVo.setTrainSectionSort(entry.getKey());
			sectionVo.setTrainSectionName(entry.getValue().get(0).getTotleSectionName());
			sectionVo.setSectionList(entry.getValue());
			sectionList.add(sectionVo);
		}
		
		ajaxData.put("sectionList", sectionList);
		//查询我的课程
		List<Course> myCourse = courseService.getCourseByUserId(userId);
		for(Course course1 : myCourse){
			if(course1.getCourseId().equals(course.getCourseId())){
				ajaxData.put("courseStatus", "1");	//1：已添加到我的课程
			}
		}
		//查询用户所属校区班级
		List<StudyTeam> teams = studyTeamService.getClassById(userId);
		ajaxData.put("teams", teams);
		
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 添加到我的课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午2:41:28
	*/
	@RequestMapping("/addMyCourse")
	@ResponseBody
	public ResponseBean addMyCourse(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		//获取请求参数
		String courseId = request.getParameter("courseId");
		String classId = request.getParameter("classId");
		
		try {
			//判断课程是否添加过我的课程
			int count = userCourseRelaService.getRelaType(courseId, userId);
			if(count > 0){
				//修改relaType
				userCourseRelaService.modifyRelaType(courseId, userId, classId);
				bean.addSuccess();
				return bean;
			}
			//添加课程信息
			int result = userCourseRelaService.addCourse(courseId, userId, classId);
			if(result == 1){
				userCourseRelaService.addAllSection(courseId, userId, classId);
				bean.addSuccess();
			}
		} catch (Exception e) {
			logger.error("--------系统异常--------", e);
			bean.addDefaultError();
		}
		return bean;
	}
	
	/**
	* 取消我的课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午2:41:28
	*/
	@RequestMapping("/cancleCourse")
	@ResponseBody
	public ResponseBean cancleCourse(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		//获取请求参数
		String courseId = request.getParameter("courseId"); 
		String classId = request.getParameter("classId");
		try {
			if(courseId != null && classId != null){
				
				userCourseRelaService.cancelCourse(courseId, userId, classId);
				bean.addSuccess(); 
			}else{
				bean.addDefaultError();
			}
		} catch (Exception e) {
			logger.error("--------系统异常--------", e);
			bean.addDefaultError();
		}
		return bean;
	}
	/**
	* 查看课程详情
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午3:41:37
	*/
	@RequestMapping("/sectionDetails")
	@ResponseBody
	public ResponseBean sectionDetails(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		String sectionId = request.getParameter("sectionId");
		String courseId = request.getParameter("courseId");
		//查询章节目录
		List<Section> list = sectionService.getSectionByCourseId(courseId);
		List<Map<String,Object>> sectionDir = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < list.size(); i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("dirSectionId", list.get(i).getSectionId());
			map.put("dirSectionName", list.get(i).getSectionName());
			map.put("dirSectionSort", list.get(i).getSectionSort());
			map.put("dirTotleSectionSort", list.get(i).getTotleSectionSort());
			sectionDir.add(map);
			//返回下一课的章节id
			if(sectionId.equals(list.get(i).getSectionId())){
				if(i+1 < list.size()){
					ajaxData.put("nextSectionId", list.get(i+1).getSectionId());
				}else{
					ajaxData.put("nextSectionId", "0");	//课程已上完
				}
			}
		}
		ajaxData.put("sectionDir", sectionDir);	//章节目录
		
		//查询章节详情
		Section section = sectionService.getSectionById(sectionId);
		
		ajaxData.put("section", section);	//本章详情

		String fileName = section.getCoursewareId()+"_"+section.getUpdateFileName();
		String savePath = fileMappingPath;
		String fileUrl = savePath +section.getSectionUrl()+"/"+fileName+".pdf";
		ajaxData.put("fileUrl", fileUrl);
		bean.addSuccess(ajaxData);
//		Map<String, Object> returnMap = null;
//		try {
//			returnMap = Pdf2ImageUtil.pdf2png(request, section.getSectionUrl(), fileName, "png");
//			if("0".equals(returnMap.get("code"))){
//				ajaxData.put("imgList", returnMap.get("imgList"));
//				ajaxData.put("pageCount", returnMap.get("pageCount"));
//				bean.addSuccess(ajaxData);
//			}else{
//				bean.addError(returnMap.get("message").toString());
//			}
//		} catch (Exception e) {
//			logger.error("------"+fileName+"文件转换异常------", e);
//			bean.addError("系统异常");
//		}
		return bean;
	}
	
	/**
	* 查看试卷信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午4:24:50
	*/
	@RequestMapping("/testDetails")
	@ResponseBody
	public ResponseBean testDetails(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String testId = request.getParameter("testId");
		//查询试卷信息
		Testpaper testPaper = testAndWorkService.getTestpaper(testId);
		bean.addSuccess(testPaper.getTestpaperPath()); 
		return bean;
	}
	
	/**
	* 查看练习信息
	* @param  
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午4:24:50
	*/
	@RequestMapping("/workDetails")
	@ResponseBody
	public ResponseBean workDetails(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String workId = request.getParameter("workId");
		//查询练习信息 
		Practice work = testAndWorkService.getPracticeById(workId);
		bean.addSuccess(work.getPracticePath());
		return bean;
	}
	
	/**
	* 全部课件下载
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午5:50:51
	*/
	/*@RequestMapping("/allDownLoad")
	@ResponseBody
	public ResponseBean allDownLoadFile(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String courseId = request.getParameter("courseId");
		Course course = courseService.getSectionByCourseId(courseId);
		//存储文件的根目录
		//String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
		String savePath = filePath;
		//服务器端创建文件临时储存总目录
		String temporaryPath = request.getSession().getServletContext().getRealPath("/WEB-INF/allDownLoad/" + userId + courseId);
		List<Section> sections = sectionService.getSectionByCourseId(courseId);
		
		for(Section section : sections){
			if(StringUtils.isEmpty(section.getSectionUrl())){
				continue;
			}
			
			String filePath = savePath + section.getSectionUrl().replace("/", "\\");
			String fileRealPath = filePath + "\\" + section.getCoursewareId() +"_"+section.getUpdateFileName() + section.getSectionType(); 
			File file = new File(fileRealPath);
			if(file.exists()){
				//服务器端创建文件临时储存二级目录
				String secondTemPath = temporaryPath + "\\"+ section.getSectionSort();
				File secondTemPathFile = new File(secondTemPath);	
				if(!secondTemPathFile.exists()){	//如果不存在目录就创建目录
					secondTemPathFile.mkdirs();
				}
				//复制文件到指定二级目录
				String toFilePath = secondTemPath + "\\" + section.getCoursewareId() +"_"+section.getUpdateFileName() + section.getSectionType();
				File toFile = new File(toFilePath);	
				try {
					Files.copy(file.toPath(), toFile.toPath());
				} catch (IOException e) {
					logger.error("------复制文件异常------"+file.getName(), e);
				}
				//查询练习信息
				if(StringUtils.isNotEmpty(section.getWorkId())){
					Practice work = testAndWorkService.getPracticeById(section.getWorkId());
					File workFile = new File(work.getPracticeUrl());	//源文件
					if(workFile.exists()){
						String[] workUrls = work.getPracticeUrl().split("\\\\");	//反斜杠切割
						String workFileName = workUrls[workUrls.length - 1];
						String workToPath = secondTemPath + "\\" + workFileName;
						File workToFile = new File(workToPath);
						try {
							Files.copy(workFile.toPath(), workToFile.toPath());
						} catch (IOException e) {
							logger.error("------复制文件异常------"+workFile.getName(), e);
						}
					}
				}
				//查询试卷信息
				if(StringUtils.isNotEmpty(section.getTestPaperId())){
					Testpaper testPaper = testAndWorkService.getTestpaper(section.getTestPaperId());
					File testFile = new File(testPaper.getTestpaperUrl());	//源文件
					if(testFile.exists()){
						String[] testUrls = testPaper.getTestpaperUrl().split("\\\\");	//反斜杠切割
						String testFileName = testUrls[testUrls.length - 1];
						String testToPath = secondTemPath + "\\" + testFileName;
						File testToFile = new File(testToPath);
						try {
							Files.copy(testFile.toPath(), testToFile.toPath());
						} catch (IOException e) {
							logger.error("------复制文件异常------"+testFile.getName(), e);
						}
					}
				}
			}
			
		}
		try {
			
			//压缩总目录
			File zipFile = cn.hutool.core.util.ZipUtil.zip(temporaryPath, temporaryPath + ".zip", true);
			ZipUtil.downloadZip(zipFile, response, course.getCourseName());
			//删除临时文件根目录
			File temporaryFile = new File(temporaryPath);
			deleteDir(temporaryFile);
			bean.addSuccess();
		} catch (Exception e) {
			logger.error("------下载文件异常------", e);
			bean.addError("系统异常");
		}
		return bean;
	}*/
	
	/**
	* 本课资源下载
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月13日 上午10:12:12
	*/
	/*@RequestMapping("/resourceDownload")
	@ResponseBody
	public ResponseBean resourceDownload(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String sectionId = request.getParameter("sectionId");
		
		List<String> fileUrl = new ArrayList<String>();
		//查询课程资源考试与练习
		Section section = sectionService.getSectionById(sectionId);
		
		if(StringUtils.isEmpty(section.getSectionUrl())){
			bean.addError("没有课件信息");
			return bean;
		}
		//String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");	//保存的文件根目录
		String savePath = filePath;
		String filePath = savePath + section.getSectionUrl().replace("/", "\\");
		String fileRealPath = filePath + "\\" + section.getCoursewareId() +"_"+section.getUpdateFileName() + section.getSectionType(); 
		fileUrl.add(fileRealPath);
		//查询练习信息
		if(StringUtils.isNotEmpty(section.getWorkId())){
			Practice work = testAndWorkService.getPracticeById(section.getWorkId());
			fileUrl.add(work.getPracticeUrl());
		}
		//查询试卷信息
		if(StringUtils.isNotEmpty(section.getTestPaperId())){
			Testpaper testPaper = testAndWorkService.getTestpaper(section.getTestPaperId());
			fileUrl.add(testPaper.getTestpaperUrl());
		}
		
		//打包文件
		List<File> files = new ArrayList<File>();
		for(String str : fileUrl){
			 File file = new File(str);
			 files.add(file);
		}
		
		//在服务器端创建保存临时文件的路径
		String tempFileName = section.getSectionName() + ".zip";
		String temporaryPath = request.getSession().getServletContext().getRealPath("/WEB-INF/download");
		File file = new File(temporaryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		String tempFilePath = temporaryPath + tempFileName;
		File tempFile = new File(tempFilePath);
		//文件输出流
        FileOutputStream outStream;
		try {
			
			if(tempFile.createNewFile()){
				outStream = new FileOutputStream(tempFile);
				ZipOutputStream toClient = new ZipOutputStream(outStream);
				ZipUtil.zipFile(files, toClient);
				toClient.close();
				outStream.close();
				ZipUtil.downloadZip(tempFile, response, section.getSectionName());
				bean.addSuccess();
			}else{
				logger.info("-------临时文件创建失败--------");
				bean.addDefaultError();
			}
		} catch (Exception e) {
			logger.error("-------系统异常--------",e);
			bean.addError("系统异常");
		}
        
		return bean;
	}*/
	
	/**
	* 将属于我的课程添加到课程列表
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年8月12日 上午10:03:40
	*/
	public void MyCourseToList(Course course, List<Course> myCourse){
		
		for(int i = 0; i <myCourse.size(); i++){
			//将属于我的课程添加进list
			if(course.getCourseId().equals(myCourse.get(i).getCourseId())){
				course.setIsBelongMe("true");
			}
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
	/*public void deleteDir(File file){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File file1 : files){
				deleteDir(file1);
			}
			file.delete();
		}else{
			file.delete();
		}
	}*/
}