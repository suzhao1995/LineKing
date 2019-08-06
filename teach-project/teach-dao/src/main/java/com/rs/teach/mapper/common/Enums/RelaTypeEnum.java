package com.rs.teach.mapper.common.Enums;

import lombok.Getter;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-05 20:39
 */
@Getter
public enum RelaTypeEnum {

    JOIN(1,"已加入我的课程"),

    NO_JOIN(2,"待加入或已取消"),

    END(0,"章节标识");


    private Integer value;

    private String label;

    RelaTypeEnum(Integer value, String label) {
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
        for (RelaTypeEnum itemEnum : RelaTypeEnum.values()) {
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
        for (RelaTypeEnum itemEnum : RelaTypeEnum.values()) {
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
        for (RelaTypeEnum itemEnum : RelaTypeEnum.values()) {
            if (itemEnum.name().equalsIgnoreCase(name)) {
                return itemEnum.getValue().intValue();
            }
        }
        return null;
    }

}
