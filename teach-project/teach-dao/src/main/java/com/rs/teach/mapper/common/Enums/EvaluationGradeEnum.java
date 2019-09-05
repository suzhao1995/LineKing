package com.rs.teach.mapper.common.Enums;

import com.rs.teach.mapper.common.OptionVo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghang
 * @Description  考核成绩枚举类
 * @create 2019-09-05 16:42
 */
@Getter
public enum EvaluationGradeEnum {

    NOT_ASSESSED(0,"未考核"),
    PENDING_REVIEW(1,"待审核"),
    NO_QUALIFIED(2,"不合格"),
    QUALIFIED(3,"合格"),
    GOOD(4,"良好"),
    EXCELLENT(5,"优秀");


    private Integer value;

    private String label;



    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    EvaluationGradeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据value获取label
     *
     * @param value
     * @return
     */
    public static String getLabel(Integer value) {
        for (EvaluationGradeEnum itemEnum : EvaluationGradeEnum.values()) {
            if (value == itemEnum.getValue().intValue()) {
                return itemEnum.getLabel();
            }
        }
        return "未知地址";
    }

    /**
     * 通过value值转为name值
     *
     * @param value
     * @return
     */
    public static String convent2TableValue(Integer value) {
        for (EvaluationGradeEnum itemEnum : EvaluationGradeEnum.values()) {
            if (itemEnum.getValue().intValue() == value) {
                return itemEnum.name();
            }
        }
        return null;
    }

    /**
     * 通过name值转为value值
     *
     * @param name
     * @return
     */
    public static Integer convent2TableNum(String name) {
        for (EvaluationGradeEnum itemEnum : EvaluationGradeEnum.values()) {
            if (itemEnum.name().equalsIgnoreCase(name)) {
                return itemEnum.getValue().intValue();
            }
        }
        return null;
    }

    /**
     * 下拉列表(不展示用户)
     */
    public static List<OptionVo> condition() {
        List<OptionVo> dataList = new ArrayList<>();
        for (EvaluationGradeEnum itemEnum : EvaluationGradeEnum.values()) {
            if (itemEnum.getValue() != 0) {
                dataList.add(new OptionVo(itemEnum.getValue(), itemEnum.getLabel()));
            }
        }
        return dataList;
    }

}
