package com.rs.teach.mapper.section.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Section.java
 *
 * @Description:培训章节实体类
 * @author: suzhao
 * @date: 2019年7月31日 下午1:27:12
 * @version: V1.0
 */
@Data
@ToString
public class TrainSection implements Serializable {


    private static final long serialVersionUID = -9077308020974792754L;

    private String trainSectionId;    //培训章节id
    private String trainCourseId;    //培训课程资源id
    private String trainSectionSort;    //培训大章节序号
    private String trainSectionName;    //培训大章节名
    private String trainLitterSectionSort;   //培训小章节序号
    private String trainLitterSectionName;   //培训小章节name
    private String trainLitterSectionUrl;    //培训小章节文件url
    private String trainLitterSectionType;   //培训小章节type(ppt,pdf,word)
    private String updateFileName;            //文件上传name
    private String extend1;    //预留字段
    private String extend2;
    private String extend3;
    private String extend4;
	public String getTrainSectionId() {
		return trainSectionId;
	}
	public void setTrainSectionId(String trainSectionId) {
		this.trainSectionId = trainSectionId;
	}
	public String getTrainCourseId() {
		return trainCourseId;
	}
	public void setTrainCourseId(String trainCourseId) {
		this.trainCourseId = trainCourseId;
	}
	public String getTrainSectionSort() {
		return trainSectionSort;
	}
	public void setTrainSectionSort(String trainSectionSort) {
		this.trainSectionSort = trainSectionSort;
	}
	public String getTrainSectionName() {
		return trainSectionName;
	}
	public void setTrainSectionName(String trainSectionName) {
		this.trainSectionName = trainSectionName;
	}
	public String getTrainLitterSectionSort() {
		return trainLitterSectionSort;
	}
	public void setTrainLitterSectionSort(String trainLitterSectionSort) {
		this.trainLitterSectionSort = trainLitterSectionSort;
	}
	public String getTrainLitterSectionName() {
		return trainLitterSectionName;
	}
	public void setTrainLitterSectionName(String trainLitterSectionName) {
		this.trainLitterSectionName = trainLitterSectionName;
	}
	public String getTrainLitterSectionUrl() {
		return trainLitterSectionUrl;
	}
	public void setTrainLitterSectionUrl(String trainLitterSectionUrl) {
		this.trainLitterSectionUrl = trainLitterSectionUrl;
	}
	public String getTrainLitterSectionType() {
		return trainLitterSectionType;
	}
	public void setTrainLitterSectionType(String trainLitterSectionType) {
		this.trainLitterSectionType = trainLitterSectionType;
	}
	public String getUpdateFileName() {
		return updateFileName;
	}
	public void setUpdateFileName(String updateFileName) {
		this.updateFileName = updateFileName;
	}
	public String getExtend1() {
		return extend1;
	}
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getExtend2() {
		return extend2;
	}
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	public String getExtend3() {
		return extend3;
	}
	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}
	public String getExtend4() {
		return extend4;
	}
	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}

    
}