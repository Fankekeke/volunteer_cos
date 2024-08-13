package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.dao.SysSchoolMapper;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 全国学校数据 实现层
 *
 * @author FanK
 */
@Service
public class SysSchoolServiceImpl extends ServiceImpl<SysSchoolMapper, SysSchool> implements ISysSchoolService {

    /**
     * 分页获取学校信息
     *
     * @param page      分页对象
     * @param sysSchool 学校信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectSchoolPage(Page<SysSchool> page, SysSchool sysSchool) {
        return baseMapper.selectSchoolPage(page, sysSchool);
    }
}
