package com.rs.teach.service.video;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;

/**
* VideoService.java
* @Description:视频模块service
* @author: suzhao
* @date: 2019年8月21日 下午12:55:31
* @version: V1.0
*/
public interface VideoService{
	
	/**
	* 根据视频分类查询视频资源
	* @param 
	* @throws
	* @return List<Video>
	* @author suzhao
	* @date 2019年8月21日 下午4:44:34
	*/
	public List<Video> getVideos(String videoType, String schoolId);

	/**
	 * 根据视频课主键查询视频章节信息
	 * @param videoId
	 * @return
	 */
	List<Section> queryVideoSectionByVideoId(String videoId);

	/**
	* 查询我的视频课程信息
	* @param
	* @throws
	* @return List<Video>
	* @author suzhao
	* @date 2019年8月22日 下午2:46:18
	*/
	public List<Map<String,Object>> MyVideo(String userId);

	/**
	* 查询单个视频课程信息
	* @param
	* @throws
	* @return Video
	* @author suzhao
	* @date 2019年8月22日 下午3:41:08
	*/
	public Video getVideoById(String videoId);

	/**
	* 获取已学完的视频课程
	* @param userId 用户id
	* @param classId 班级id
	* @param videoId 视频课程id
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年8月22日 下午4:49:59
	*/
	public List<Map<String,Object>> getFinishStudy(String userId, String classId, String videoId);

	/**
	* 根据videoId查询视频课程章节信息
	* @param
	* @throws
	* @return List<VideoSection>
	* @author suzhao
	* @date 2019年8月22日 下午5:29:29
	*/
	public List<VideoSection> getVideoSection(String videoId);

	/**
	* 根据videoSectionId查询视频章节信息
	* @param videoSectionId 视频课件id
	* @throws
	* @return VideoSection
	* @author suzhao
	* @date 2019年8月23日 下午4:20:21
	*/
	public VideoSection getSectionBySecId(String videoSectionId);

	/**
	* 查询各视频课件学习状态
	* @param
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年8月23日 下午4:57:19
	*/
	public List<Map<String,Object>> getSectionStatus(String videoId,String userId, String classId);
	
	//管理员模块 start
	
	/**
	* 根据videoType查询视频课程信息
	* @param 
	* @throws
	* @return List<Video>
	* @author suzhao
	* @date 2019年9月2日 下午5:44:11
	*/
	public List<Video> adminGetVideos(String videoType);
	
	/**
	* 根据视频名称 和 视频分类查询视频列表
	* @param 
	* @throws
	* @return List<Video>
	* @author suzhao
	* @date 2019年9月2日 下午5:45:44
	*/
	public List<Video> adminVideosInit(String videoName, String videoType);
	
	/**
	* 新增课程
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月3日 上午11:11:27
	*/
	public void adminAddVideo(Video video);
	
	/**
	* 删除课程
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月3日 上午11:28:34
	*/
	public void adminDelVideo(String videoId);
	
	/**
	* 修改视频资料
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月3日 下午12:01:37
	*/
	public void adminUpdate(Video video);
	
	/**
	* 根据videoId查询大章节序号
	* @param 
	* @throws
	* @return List<String>
	* @author suzhao
	* @date 2019年9月3日 下午2:16:08
	*/
	public List<Map<String,String>> adminTotleInfo(String videoId);
	
	/**
	* 根据video查询视频课程的所有章节信息(包含未添加小章节的大章节信息)
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年9月3日 下午3:52:09
	*/
	public List<Map<String,Object>> adminGetVideoInfo(String videoId);
	
	/**
	* 新增大章节
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年9月3日 下午4:50:39
	*/
	public int adminAddTotleInfo(TotleSection totleSection);
	
	/**
	* 根据id查询大章节
	* @param 
	* @throws
	* @return TotleSection
	* @author suzhao
	* @date 2019年9月3日 下午5:07:55
	*/
	public TotleSection getTotleSection(String id);
	
	/**
	* 修改大章节名
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月3日 下午5:14:07
	*/
	public void updateTotleSection(TotleSection totleSection);
	
	/**
	* 根据大章节id 和 视频id查询视频课件信息
	* @param 
	* @throws
	* @return List<VideoSection>
	* @author suzhao
	* @date 2019年9月4日 下午12:17:44
	*/
	public List<VideoSection> adminGetVedioSection(String totleSortId, String videoId);
	
	/**
	* 上传视频课件
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月4日 下午3:11:32
	*/
	public void insertVideoSection(VideoSection videoSection,Practice work, Testpaper test);
	
	/**
	* 删除视频课件
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月4日 下午3:44:41
	*/
	public void delVideoSection(String videoSectionId);
	
	/**
	* 修改视频课件
	* @param oldSection 修改之前的文件
	* @param videoSection 新文件
	* @param work 作业
	* @param test 试卷
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月4日 下午5:39:43
	*/
	public void updateVideoSection(VideoSection oldSection ,VideoSection videoSection,Practice work, Testpaper test);

	/**
	 * 视频课程数量
	 * @return
	 */
    Integer selectVideoNum();

}