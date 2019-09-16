package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.backstage.vo.SchoolVo;
import com.rs.teach.mapper.common.OptionVo;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 11:13
 */
public interface SchoolService {
    /**
     * 新增学校
     * @param school
     */
    void addSchool(School school) throws Exception;

    /**
     * 删除学校
     * @param schoolId
     */
    void deleteSchool(String schoolId);

    /**
     * 修改学校
     * @param school
     */
    void updateSchool(School school);

    /**
     * 查询学校信息（下拉列表）
     * @return
     */
    List<OptionVo> selectSchool();

    /**
     * 分页查询
     */
    List<SchoolVo> selectSchoolVo();

    /**
     * 学校是否存在
     * @param school
     * @return
     */
    Integer isEmpty(School school);

    /**
     * 数据回显
     * @param school
     * @return
     */
    SchoolVo selectSchoolBySchoolId(School school);
}
