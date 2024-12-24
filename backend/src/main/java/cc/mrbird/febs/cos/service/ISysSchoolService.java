package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SysSchool;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 学校信息 service层
 *
 * @author FanK
 */
public interface ISysSchoolService extends IService<SysSchool> {

    /**
     * 分页获取学校信息
     *
     * @param page      分页对象
     * @param sysSchool 学校信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectSchoolPage(Page<SysSchool> page, SysSchool sysSchool);

    /**
     * 获取首页统计数据
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();

    /**
     * 逆地址解析
     */
    void addressResolve();

    /**
     * 获取省份信息
     *
     * @return 结果
     */
    List<String> queryProvince();
}
