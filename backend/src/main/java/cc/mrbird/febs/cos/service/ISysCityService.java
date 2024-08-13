package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SysCity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 全国城市数据 service层
 *
 * @author FanK
 */
public interface ISysCityService extends IService<SysCity> {

    /**
     * 分页获取城市信息
     *
     * @param page    分页对象
     * @param sysCity 城市信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCityPage(Page<SysCity> page, SysCity sysCity);
}
