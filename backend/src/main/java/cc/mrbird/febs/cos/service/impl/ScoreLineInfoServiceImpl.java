package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.dao.ScoreLineInfoMapper;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.entity.vo.ScoreLineVo;
import cc.mrbird.febs.cos.service.IScoreLineInfoService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分数线 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScoreLineInfoServiceImpl extends ServiceImpl<ScoreLineInfoMapper, ScoreLineInfo> implements IScoreLineInfoService {

    private final ISysSchoolService sysSchoolService;


    /**
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectScoreLinePage(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) {
        return baseMapper.selectScoreLinePage(page, scoreLineInfo);
    }

    /**
     * 根据学校获取分数线
     *
     * @param schoolId 学校ID
     * @param year     年份
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectScoreLineBySchool(Integer schoolId, String year) {
        List<LinkedHashMap<String, Object>> resultList = baseMapper.selectScoreLineBySchool(schoolId, year);
        if (CollectionUtil.isNotEmpty(resultList)) {
            return resultList;
        }
        return baseMapper.selectScoreLineDefault();
    }

    /**
     * 获取分数线地图信息
     *
     * @param year 年份
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectScoreMap(Integer year) {
        if (year == null) {
            year = DateUtil.year(new Date());
        }
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();

        // 校院信息
        List<SysSchool> schoolList = sysSchoolService.list();
        List<Integer> schoolIds = schoolList.stream().map(SysSchool::getId).collect(Collectors.toList());

        // 获取学校分数线
        List<ScoreLineVo> scoreLineVoList = baseMapper.selectScoreLineBySchoolIds(schoolIds, year.toString());
        Map<Integer, List<ScoreLineVo>> scoreLineMap = scoreLineVoList.stream().collect(Collectors.groupingBy(ScoreLineVo::getSchoolId));

        // 默认分数线
        List<LinkedHashMap<String, Object>> defaultList = baseMapper.selectScoreLineDefault();

        for (SysSchool school : schoolList) {
            List<ScoreLineVo> scoreLine = CollectionUtil.isNotEmpty(scoreLineMap) ? scoreLineMap.get(school.getId()) : Collections.emptyList();
            result.add(new LinkedHashMap<String, Object>() {
                {
                    put("school", school);
                    put("scoreLine", CollectionUtil.isNotEmpty(scoreLine) ? scoreLine : defaultList);
                }
            });
        }
        return result;
    }
}
