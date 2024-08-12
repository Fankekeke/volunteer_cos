package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.DisciplineInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 专业管理 service层
 *
 * @author FanK
 */
public interface IDisciplineInfoService extends IService<DisciplineInfo> {

    /**
     * 分页获取专业信息
     *
     * @param page           分页对象
     * @param disciplineInfo 专业信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectDisciplinePage(Page<DisciplineInfo> page, DisciplineInfo disciplineInfo);
}
