package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SchoolDisciplineBind;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 学校专业绑定 mapper层
 *
 * @author FanK
 */
public interface SchoolDisciplineBindMapper extends BaseMapper<SchoolDisciplineBind> {

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param schoolDisciplineBind 学校专业绑定信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectDisciplineBindPage(Page<SchoolDisciplineBind> page, @Param("schoolDisciplineBind") SchoolDisciplineBind schoolDisciplineBind);

    /**
     * 根据学校ID获取绑定信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectBindBySchool(@Param("schoolId")Integer schoolId);
}
