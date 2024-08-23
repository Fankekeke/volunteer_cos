package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.dao.ScoreLineInfoMapper;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.entity.vo.ScoreLineVo;
import cc.mrbird.febs.cos.service.IScoreLineInfoService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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

    /**
     * 获取推荐学校
     *
     * @param score        分数
     * @param disciplineId 专业ID
     * @param type         类型
     * @return
     */
    @Override
    public List<ScoreLineVo> selectRecommendSchool(Integer score, Integer disciplineId, String type) {
        // 返回数据
        List<ScoreLineVo> result = new ArrayList<>();

        // 校院信息
        List<SysSchool> schoolList = sysSchoolService.list();
        List<Integer> schoolIds = schoolList.stream().map(SysSchool::getId).collect(Collectors.toList());

        int year = DateUtil.year(new Date());

        // 获取学校分数线
        List<ScoreLineVo> scoreLineVoList = baseMapper.selectScoreLineBySchoolIds(schoolIds, StrUtil.toString(year));
        Map<Integer, List<ScoreLineVo>> scoreLineMap = scoreLineVoList.stream().collect(Collectors.groupingBy(ScoreLineVo::getSchoolId));

        // 默认分数线
        List<ScoreLineVo> defaultList = baseMapper.selectScoreLineDefaultFix();
        Map<String, List<ScoreLineVo>> defaultScoreLineMap = defaultList.stream().collect(Collectors.groupingBy(e -> e.getDisciplineId() + "|" + e.getType()));

        for (SysSchool school : schoolList) {

            // 学校分数线
            List<ScoreLineVo> currentList = CollectionUtil.isNotEmpty(scoreLineMap) ? scoreLineMap.get(school.getId()) : Collections.emptyList();

            // 专业+类型分数线
            List<ScoreLineVo> disciplineScoreLineList;

            if (CollectionUtil.isNotEmpty(currentList)) {
                Map<String, List<ScoreLineVo>> currentScoreLineMap = currentList.stream().collect(Collectors.groupingBy(e -> e.getDisciplineId() + "|" + e.getType()));
                disciplineScoreLineList = currentScoreLineMap.get(disciplineId + "|" + type);
            } else {
                disciplineScoreLineList = defaultScoreLineMap.get(disciplineId + "|" + type);
            }

            if (CollectionUtil.isEmpty(disciplineScoreLineList)) {
                continue;
            }

            // 高于分数线的信息
            List<ScoreLineVo> passLine = disciplineScoreLineList.stream().filter(e -> e.getScore() <= score).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(passLine)) {
                continue;
            }

            result.addAll(passLine);
        }
        return result;
    }

    /**
     * 校院统计
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectSchoolRate() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("area", Collections.emptyMap());
                put("manage", Collections.emptyMap());
                put("level", Collections.emptyMap());
            }
        };

        // 校院信息
        List<SysSchool> schoolList = sysSchoolService.list();

        // 按地区分类
        Map<String, List<SysSchool>> areaMap = schoolList.stream().collect(Collectors.groupingBy(SysSchool::getProvince));
        // 按主管部门分类
        Map<String, List<SysSchool>> manageMap = schoolList.stream().collect(Collectors.groupingBy(SysSchool::getManage));
        // 按层次分类
        Map<String, List<SysSchool>> levelMap = schoolList.stream().collect(Collectors.groupingBy(SysSchool::getLevel));

        result.put("area", areaMap);
        result.put("manage", manageMap);
        result.put("level", levelMap);
        return result;
    }

    /**
     * 招生统计
     *
     * @param year 年份
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectAdmissionsRate(Integer year) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("area", Collections.emptyMap());
                put("manage", Collections.emptyMap());
                put("level", Collections.emptyMap());
                put("discipline", Collections.emptyMap());
            }
        };

        // 校院信息
        List<SysSchool> schoolList = sysSchoolService.list();
        List<Integer> schoolIds = schoolList.stream().map(SysSchool::getId).collect(Collectors.toList());

        // 按地区分类
        Map<String, List<SysSchool>> areaMap = schoolList.stream().collect(Collectors.groupingBy(SysSchool::getProvince));
        // 按主管部门分类
        Map<String, List<SysSchool>> manageMap = schoolList.stream().collect(Collectors.groupingBy(SysSchool::getManage));
        // 按层次分类
        Map<String, List<SysSchool>> levelMap = schoolList.stream().collect(Collectors.groupingBy(SysSchool::getLevel));

        // 获取学校分数线
        List<ScoreLineVo> scoreLineVoList = baseMapper.selectScoreLineBySchoolIds(schoolIds, StrUtil.toString(year));
        Map<Integer, List<ScoreLineVo>> scoreLineMap = scoreLineVoList.stream().collect(Collectors.groupingBy(ScoreLineVo::getSchoolId));

        // 默认分数线
        List<ScoreLineVo> defaultList = baseMapper.selectScoreLineDefaultFix();
        return null;
    }
}
