package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.vo.AnswerSheetVo;

import java.util.List;

/**
 * @author wanghang
 * @Description  答卷表
 * @create 2019-09-05 12:33
 */
public interface AnswerSheetService {
    /**
     * 添加答卷表，评价表和修改考核人员与考核文件关联表的ANSWER_SHEET_ID
     * @param answerSheet
     * @param id
     */
    void addAnswerSheet(AnswerSheet answerSheet, String id);

    /**
     * 查询当前用户的上传答卷信息
     * @param answerSheetId
     * @return
     */
    AnswerSheet selectAnswerSheet(String answerSheetId);

    /**
     * 根据培训考核文件表主键查询考核人员上传答卷信息
     * @param id
     * @return
     */
    List<AnswerSheetVo> queryAnswerSheet(String id);

    /**
     * 根据id删除答卷表数据
     * @param answerSheetId
     */
    void deleteAnswerSheetById(String answerSheetId);


}
