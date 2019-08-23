package com.rs.teach.service.video;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.section.entity.Section;
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
	public List<Video> getVideos(String videoType);

	/**
	 * 查询电影章节信息
	 * @param videoSectionId
	 * @return
	 */
	VideoSection selectVideoSection(String videoSectionId);

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
}