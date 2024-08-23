package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SysSchool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 学校信息 mapper层
 *
 * @author FanK
 */
public interface SysSchoolMapper extends BaseMapper<SysSchool> {

    /**
     * 分页获取学校信息
     *
     * @param page      分页对象
     * @param sysSchool 学校信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectSchoolPage(Page<SysSchool> page, @Param("sysSchool") SysSchool sysSchool);
}
