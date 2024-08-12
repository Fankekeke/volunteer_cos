package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DisciplineInfo;
import cc.mrbird.febs.cos.dao.DisciplineInfoMapper;
import cc.mrbird.febs.cos.service.IDisciplineInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 专业管理 实现层
 *
 * @author FanK
 */
@Service
public class DisciplineInfoServiceImpl extends ServiceImpl<DisciplineInfoMapper, DisciplineInfo> implements IDisciplineInfoService {

    /**
     * 分页获取专业信息
     *
     * @param page           分页对象
     * @param disciplineInfo 专业信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectDisciplinePage(Page<DisciplineInfo> page, DisciplineInfo disciplineInfo) {
        return baseMapper.selectDisciplinePage(page, disciplineInfo);
    }
}
