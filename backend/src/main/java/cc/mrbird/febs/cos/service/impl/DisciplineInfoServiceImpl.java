package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DisciplineInfo;
import cc.mrbird.febs.cos.dao.DisciplineInfoMapper;
import cc.mrbird.febs.cos.service.IDisciplineInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public IPage<DisciplineInfo> selectDisciplinePage(Page<DisciplineInfo> page, DisciplineInfo disciplineInfo) {
        IPage<DisciplineInfo> result = baseMapper.selectDisciplinePage(page, disciplineInfo);
//        List<DisciplineInfo> disciplineInfos = result.getRecords();
//        if (CollectionUtil.isEmpty(disciplineInfos)) {
//            return result;
//        }
//        List<String> codes = disciplineInfos.stream().map(DisciplineInfo::getCode).collect(Collectors.toList());
//        List<DisciplineInfo> children = this.list(Wrappers.<DisciplineInfo>lambdaQuery().in(DisciplineInfo::getParentCode, codes));
//        Map<String, List<DisciplineInfo>> childrenMap = children.stream().collect(Collectors.groupingBy(DisciplineInfo::getParentCode));
//        for (DisciplineInfo record : result.getRecords()) {
//            List<DisciplineInfo> currentChildren = childrenMap.get(record.getCode());
//            if (CollectionUtil.isEmpty(currentChildren)) {
//                record.setChildren(Collections.emptyList());
//                continue;
//            }
//            record.setChildren(currentChildren);
//        }
        return result;
    }
}
