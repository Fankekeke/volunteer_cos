package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SchoolDisciplineBind;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

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
}
