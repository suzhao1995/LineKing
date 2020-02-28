package com.rs.teach.mapper.grade.vo;

import com.rs.teach.mapper.grade.entity.GradePreschoolOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2020-02-27 1:19
 */
@Data
public class GradeVo implements Serializable {
    private static final long serialVersionUID = 4063209839849511433L;

    /**
     * 题目id
     */
    private Integer preschoolId;

    /**
     * 题目
     */
    private String question;

    /**
     * 题号
     */
    private Integer sort;

    /**
     * 题目音频
     */
    private String questionAudio;

    /**
     * 正确答案
     */
    private String rightAnswer;

    /**
     * 题目图片
     */
    private String questionPicture;

    /**
     * 答案选项
     */
    private List<GradePreschoolOption> list;

    /**
     * 正确率
     */
    private String acc;

    /**
     * 题目宝宝id
     */
    private Integer babyId;
}
