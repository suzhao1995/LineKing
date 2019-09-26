package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.vo.AnswerSheetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 12:34
 */
public interface AnswerSheetMapper {

    /**
     * 添加考核人员上传答卷表
     * @param answerSheet
     */
    void addAnswerSheet(AnswerSheet answerSheet);

    /**
     * 查询主键Id
     * @param answerSheet
     * @return
     */
    String selectAnswerSheetId(AnswerSheet answerSheet);

    /**
     * 查询当前用户的上传答卷信息
     * @param answerSheetId
     * @return
     */
    AnswerSheet selectAnswerSheet(@Param("answerSheetId") String answerSheetId);

    /**
     * 根据培训考核文件表主键查询考核人员上传答卷信息
     * @param trainData
     * @return
     */
    List<AnswerSheetVo> queryAnswerSheet(TrainData trainData);

    /**
     * 根据id删除答卷表数据
     * @param answerSheetId
     */
    void deleteAnswerSheetById(@Param("answerSheetId") String answerSheetId);

}
