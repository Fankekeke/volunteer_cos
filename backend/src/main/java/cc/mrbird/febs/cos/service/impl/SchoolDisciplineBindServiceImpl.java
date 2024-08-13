package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SchoolDisciplineBind;
import cc.mrbird.febs.cos.dao.SchoolDisciplineBindMapper;
import cc.mrbird.febs.cos.service.ISchoolDisciplineBindService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 学校专业绑定 实现层
 *
 * @author FanK
 */
@Service
public class SchoolDisciplineBindServiceImpl extends ServiceImpl<SchoolDisciplineBindMapper, SchoolDisciplineBind> implements ISchoolDisciplineBindService {

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param schoolDisciplineBind 学校专业绑定信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectDisciplineBindPage(Page<SchoolDisciplineBind> page, SchoolDisciplineBind schoolDisciplineBind) {
        return baseMapper.selectDisciplineBindPage(page, schoolDisciplineBind);
    }

    @Override
    public List<LinkedHashMap<String, Object>> selectBindBySchool(Integer schoolId) {
        return baseMapper.selectBindBySchool(schoolId);
    }
}
