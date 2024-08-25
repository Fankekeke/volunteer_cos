package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.Professional;
import cc.mrbird.febs.cos.dao.ProfessionalMapper;
import cc.mrbird.febs.cos.service.IProfessionalService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 学校专业 实现层
 *
 * @author FanK
 */
@Service
public class ProfessionalServiceImpl extends ServiceImpl<ProfessionalMapper, Professional> implements IProfessionalService {

    /**
     * 分页获取学校专业信息
     *
     * @param page         分页对象
     * @param professional 学校专业信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectProfessionalPage(Page<Professional> page, Professional professional) {
        return baseMapper.selectProfessionalPage(page, professional);
    }
}
