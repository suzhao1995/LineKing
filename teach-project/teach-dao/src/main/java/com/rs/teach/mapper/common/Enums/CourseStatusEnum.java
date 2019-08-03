package com.rs.teach.mapper.common.Enums;

import lombok.Getter;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 10:46
 */
@Getter
public enum CourseStatusEnum {

    NO_SIGN(0, "待学习"),

    STARTING(1, "学习中"),

    END(2, "已学完");

    private Integer value;

    private String label;

    CourseStatusEnum(Integer value, String label) {
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
        for (CourseStatusEnum itemEnum : CourseStatusEnum.values()) {
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
        for (CourseStatusEnum itemEnum : CourseStatusEnum.values()) {
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
        for (CourseStatusEnum itemEnum : CourseStatusEnum.values()) {
            if (itemEnum.name().equalsIgnoreCase(name)) {
                return itemEnum.getValue().intValue();
            }
        }
        return null;
    }

}
