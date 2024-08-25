package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.Professional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 学校专业 service层
 *
 * @author FanK
 */
public interface IProfessionalService extends IService<Professional> {

    /**
     * 分页获取学校专业信息
     *
     * @param page         分页对象
     * @param professional 学校专业信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectProfessionalPage(Page<Professional> page, Professional professional);
}
