package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.DisciplineInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 专业管理 mapper层
 *
 * @author FanK
 */
public interface DisciplineInfoMapper extends BaseMapper<DisciplineInfo> {

    /**
     * 分页获取专业信息
     *
     * @param page           分页对象
     * @param disciplineInfo 专业信息
     * @return 结果
     */
    IPage<DisciplineInfo> selectDisciplinePage(Page<DisciplineInfo> page, @Param("disciplineInfo") DisciplineInfo disciplineInfo);
}
