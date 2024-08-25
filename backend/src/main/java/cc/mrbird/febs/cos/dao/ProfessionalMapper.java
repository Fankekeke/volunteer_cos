package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.Professional;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 学校专业 mapper层
 *
 * @author FanK
 */
public interface ProfessionalMapper extends BaseMapper<Professional> {

    /**
     * 分页获取学校专业信息
     *
     * @param page         分页对象
     * @param professional 学校专业信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectProfessionalPage(Page<Professional> page, @Param("professional") Professional professional);
}
