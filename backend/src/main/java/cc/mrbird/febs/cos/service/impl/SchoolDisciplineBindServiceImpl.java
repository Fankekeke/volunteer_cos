package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SchoolDisciplineBind;
import cc.mrbird.febs.cos.dao.SchoolDisciplineBindMapper;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.service.ISchoolDisciplineBindService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 学校专业绑定 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchoolDisciplineBindServiceImpl extends ServiceImpl<SchoolDisciplineBindMapper, SchoolDisciplineBind> implements ISchoolDisciplineBindService {

    private final ISysSchoolService sysSchoolService;

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

    /**
     * 根据学校账户ID获取绑定信息
     *
     * @param userId 学校账户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectBindBySchoolUserId(Integer userId) {
        // 获取学校信息
        SysSchool school = sysSchoolService.getOne(Wrappers.<SysSchool>lambdaQuery().eq(SysSchool::getUserId, userId));
        if (school == null) {
            return Collections.emptyList();
        }
        return baseMapper.selectBindBySchool(school.getId());
    }
}
