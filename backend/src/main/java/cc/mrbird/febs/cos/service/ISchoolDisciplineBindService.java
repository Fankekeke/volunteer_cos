package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SchoolDisciplineBind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 学校专业绑定 service层
 *
 * @author FanK
 */
public interface ISchoolDisciplineBindService extends IService<SchoolDisciplineBind> {

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param schoolDisciplineBind 学校专业绑定信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectDisciplineBindPage(Page<SchoolDisciplineBind> page, SchoolDisciplineBind schoolDisciplineBind);
}
