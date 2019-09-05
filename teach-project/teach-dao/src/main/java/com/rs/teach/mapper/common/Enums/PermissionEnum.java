package com.rs.teach.mapper.common.Enums;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.common.OptionVo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-05 20:39
 */
@Getter
public enum PermissionEnum {

    user_("0", "用户"),
    admin("1", "管理员"),
    supperAdmin("2", "超级管理员");

    private String value;

    private String label;

    PermissionEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 根据value获取label
     *
     * @param value
     * @return
     */
    public static String getLabel(String value) {
        for (PermissionEnum itemEnum : PermissionEnum.values()) {
            if (itemEnum.getValue().equalsIgnoreCase(value)) {
                return itemEnum.getLabel();
            }
        }
        return "未知权限";
    }

    /**
     * 通过value值转为name值
     *
     * @param value
     * @return
     */
    public static String convent2TableValue(String value) {
        for (PermissionEnum itemEnum : PermissionEnum.values()) {
            if (itemEnum.getValue().equalsIgnoreCase(value)) {
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
    public static String convent2TableNum(String name) {
        for (PermissionEnum itemEnum : PermissionEnum.values()) {
            if (itemEnum.name().equalsIgnoreCase(name)) {
                return itemEnum.getValue();
            }
        }
        return null;
    }

    /**
     * 下拉列表(不展示用户)
     */
    public static List<OptionVo> condition() {
        List<OptionVo> dataList = new ArrayList<>();
        for (PermissionEnum itemEnum : PermissionEnum.values()) {
            if (!StrUtil.equalsIgnoreCase(itemEnum.getValue(), "0")) {
                dataList.add(new OptionVo(itemEnum.getValue(), itemEnum.getLabel()));
            }
        }
        return dataList;
    }
}
