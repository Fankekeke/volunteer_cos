package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SysCity;
import cc.mrbird.febs.cos.dao.SysCityMapper;
import cc.mrbird.febs.cos.service.ISysCityService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 全国城市数据 实现层
 *
 * @author FanK
 */
@Service
public class SysCityServiceImpl extends ServiceImpl<SysCityMapper, SysCity> implements ISysCityService {

    /**
     * 分页获取城市信息
     *
     * @param page    分页对象
     * @param sysCity 城市信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCityPage(Page<SysCity> page, SysCity sysCity) {
        return baseMapper.selectCityPage(page, sysCity);
    }
}
