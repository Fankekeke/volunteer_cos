package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.ScoreLineInfoMapper;
import cc.mrbird.febs.cos.entity.vo.ScoreLineRecommendVo;
import cc.mrbird.febs.cos.entity.vo.ScoreLineVo;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    private final IUserInfoService userInfoService;

    private final IUserWishDisciplineService userWishDispatcherService;

    private final IUserWishInfoService userWishInfoService;


    /**
     * 分页获取学校专业绑定信息
     *
     * @param page          分页对象
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectScoreLinePage(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) {
        return baseMapper.selectScoreLinePage(page, scoreLineInfo);
    }

    /**
     * 根据学校ID获取招生信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectScoreLineBySchoolId(Integer schoolId) {
        return baseMapper.selectScoreLineBySchoolId(schoolId);
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
     * @param page          分页对象
     * @param scoreLineInfo 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecommendSchool(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) throws FebsException {

        // 获取用户志愿专业与志愿学校
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, scoreLineInfo.getUserId()));
        if (userInfo != null) {
            scoreLineInfo.setUserId(userInfo.getId());
            scoreLineInfo.setType(userInfo.getType());
            scoreLineInfo.setScore(userInfo.getScore() == null ? 0 : userInfo.getScore());
        }

        List<UserWishDiscipline> wishDisciplineList = userWishDispatcherService.list(Wrappers.<UserWishDiscipline>lambdaQuery().eq(UserWishDiscipline::getUserId, scoreLineInfo.getUserId()));
        List<UserWishInfo> wishInfoList = userWishInfoService.list(Wrappers.<UserWishInfo>lambdaQuery().eq(UserWishInfo::getUserId, scoreLineInfo.getUserId()));

        List<Integer> disciplineIdList = wishDisciplineList.stream().map(UserWishDiscipline::getDisciplineId).distinct().collect(Collectors.toList());

        List<Integer> schoolIdList = wishInfoList.stream().map(UserWishInfo::getSchoolId).distinct().collect(Collectors.toList());
        List<Integer> wishDisciplineIdList = wishInfoList.stream().map(UserWishInfo::getDisciplineId).distinct().collect(Collectors.toList());
        CollectionUtil.addAll(disciplineIdList, wishDisciplineIdList);

//        // 校院信息
//        List<SysSchool> schoolList = sysSchoolService.list(Wrappers.<SysSchool>lambdaQuery().in(CollectionUtil.isNotEmpty(schoolIdList), SysSchool::getId, schoolIdList));
//        List<Integer> schoolIds = schoolList.stream().map(SysSchool::getId).collect(Collectors.toList());

        int year = DateUtil.year(new Date());
        scoreLineInfo.setYear(String.valueOf(year));

//        // 获取学校分数线
//        List<ScoreLineVo> scoreLineVoList = baseMapper.selectScoreLineBySchoolIds(schoolIds, StrUtil.toString(year));
//        Map<Integer, List<ScoreLineVo>> scoreLineMap = scoreLineVoList.stream().collect(Collectors.groupingBy(ScoreLineVo::getSchoolId));
//
//        // 默认分数线
//        List<ScoreLineVo> defaultList = baseMapper.selectScoreLineDefaultFix();
//        Map<String, List<ScoreLineVo>> defaultScoreLineMap = defaultList.stream().collect(Collectors.groupingBy(e -> e.getDisciplineCode() + "|" + e.getType()));
//
//        for (SysSchool school : schoolList) {
//
//            // 学校分数线
//            List<ScoreLineVo> currentList = CollectionUtil.isNotEmpty(scoreLineMap) ? scoreLineMap.get(school.getId()) : Collections.emptyList();
//
//            // 专业+类型分数线
//            List<ScoreLineVo> disciplineScoreLineList;
//
//            if (CollectionUtil.isNotEmpty(currentList)) {
//                Map<String, List<ScoreLineVo>> currentScoreLineMap = currentList.stream().collect(Collectors.groupingBy(e -> e.getDisciplineCode() + "|" + e.getType()));
//                disciplineScoreLineList = currentScoreLineMap.get(disciplineId + "|" + type);
//            } else {
//                disciplineScoreLineList = defaultScoreLineMap.get(disciplineId + "|" + type);
//            }
//
//            if (CollectionUtil.isEmpty(disciplineScoreLineList)) {
//                continue;
//            }
//
//            // 高于分数线的信息
//            List<ScoreLineVo> passLine = disciplineScoreLineList.stream().filter(e -> e.getScore() <= score).collect(Collectors.toList());
//            if (CollectionUtil.isEmpty(passLine)) {
//                continue;
//            }
//
//            result.addAll(passLine);
//        }
        return baseMapper.selectScoreLineRecommendPage(page, scoreLineInfo, schoolIdList, disciplineIdList);
    }

    /**
     * 根据专业获取推荐学校
     *
     * @param scoreLineInfo 参数
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRecommendByMaior(ScoreLineInfo scoreLineInfo) throws FebsException {
        // 获取用户志愿专业与志愿学校
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, scoreLineInfo.getUserId()));
        if (userInfo != null) {
            scoreLineInfo.setUserId(userInfo.getId());
            scoreLineInfo.setType(userInfo.getType());
            scoreLineInfo.setScore(userInfo.getScore() == null ? 0 : userInfo.getScore());
        }

        List<UserWishDiscipline> wishDisciplineList = userWishDispatcherService.list(Wrappers.<UserWishDiscipline>lambdaQuery().eq(UserWishDiscipline::getUserId, scoreLineInfo.getUserId()));
        List<UserWishInfo> wishInfoList = userWishInfoService.list(Wrappers.<UserWishInfo>lambdaQuery().eq(UserWishInfo::getUserId, scoreLineInfo.getUserId()));

        List<Integer> disciplineIdList = wishDisciplineList.stream().map(UserWishDiscipline::getDisciplineId).distinct().collect(Collectors.toList());

        List<Integer> schoolIdList = wishInfoList.stream().map(UserWishInfo::getSchoolId).distinct().collect(Collectors.toList());
        List<Integer> wishDisciplineIdList = wishInfoList.stream().map(UserWishInfo::getDisciplineId).distinct().collect(Collectors.toList());
        CollectionUtil.addAll(disciplineIdList, wishDisciplineIdList);

        int year = DateUtil.year(new Date());
        scoreLineInfo.setYear(String.valueOf(year));
        List<ScoreLineRecommendVo> scoreLineRecommendVoList = baseMapper.selectScoreLineRecommend(scoreLineInfo, schoolIdList, disciplineIdList);
        if (CollectionUtil.isEmpty(scoreLineRecommendVoList)) {
            return Collections.emptyList();
        }
        Map<String, List<ScoreLineRecommendVo>> map = scoreLineRecommendVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getDisciplineName())).collect(Collectors.groupingBy(ScoreLineRecommendVo::getDisciplineName));
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();

        map.forEach((key, value) -> {
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("name", key);
            linkedHashMap.put("list", value);
            result.add(linkedHashMap);
        });
        return result;
    }

    /**
     * 根据省份获取推荐学校
     * @param scoreLineInfo 参数
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRecommendProvinces(ScoreLineInfo scoreLineInfo) throws FebsException {
        // 获取用户志愿专业与志愿学校
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, scoreLineInfo.getUserId()));
        if (userInfo != null) {
            scoreLineInfo.setUserId(userInfo.getId());
            scoreLineInfo.setType(userInfo.getType());
            scoreLineInfo.setScore(userInfo.getScore() == null ? 0 : userInfo.getScore());
        }

        List<UserWishDiscipline> wishDisciplineList = userWishDispatcherService.list(Wrappers.<UserWishDiscipline>lambdaQuery().eq(UserWishDiscipline::getUserId, scoreLineInfo.getUserId()));
        List<UserWishInfo> wishInfoList = userWishInfoService.list(Wrappers.<UserWishInfo>lambdaQuery().eq(UserWishInfo::getUserId, scoreLineInfo.getUserId()));

        List<Integer> disciplineIdList = wishDisciplineList.stream().map(UserWishDiscipline::getDisciplineId).distinct().collect(Collectors.toList());

        List<Integer> schoolIdList = wishInfoList.stream().map(UserWishInfo::getSchoolId).distinct().collect(Collectors.toList());
        List<Integer> wishDisciplineIdList = wishInfoList.stream().map(UserWishInfo::getDisciplineId).distinct().collect(Collectors.toList());
        CollectionUtil.addAll(disciplineIdList, wishDisciplineIdList);

        int year = DateUtil.year(new Date());
        scoreLineInfo.setYear(String.valueOf(year));
        List<ScoreLineRecommendVo> scoreLineRecommendVoList = baseMapper.selectScoreLineRecommend(scoreLineInfo, schoolIdList, disciplineIdList);
        if (CollectionUtil.isEmpty(scoreLineRecommendVoList)) {
            return Collections.emptyList();
        }
        Map<String, List<ScoreLineRecommendVo>> map = scoreLineRecommendVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getProvince())).collect(Collectors.groupingBy(ScoreLineRecommendVo::getProvince));
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();

        map.forEach((key, value) -> {
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("name", key);
            linkedHashMap.put("list", value);
            result.add(linkedHashMap);
        });
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
        Map<String, List<SysSchool>> areaMap = schoolList.stream().filter(e -> StrUtil.isNotEmpty(e.getProvince())).collect(Collectors.groupingBy(SysSchool::getProvince));
        // 按主管部门分类
        Map<String, List<SysSchool>> manageMap = schoolList.stream().filter(e -> StrUtil.isNotEmpty(e.getManage())).collect(Collectors.groupingBy(SysSchool::getManage));
        // 按层次分类
        Map<String, List<SysSchool>> levelMap = schoolList.stream().filter(e -> StrUtil.isNotEmpty(e.getType())).collect(Collectors.groupingBy(SysSchool::getType));

        result.put("area", areaMap);
        result.put("manage", manageMap);
        result.put("level", levelMap);
        return result;
    }

    /**
     * 校院统计1
     *
     * @param type 类型
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectSchoolRateByType(String type) {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();

        // 校院信息
        List<SysSchool> schoolList = sysSchoolService.list();

        Map<String, List<SysSchool>> schoolMap = new HashMap<>();
        if ("1".equals(type)) {
            // 按地区分类
            schoolMap = schoolList.stream().filter(e -> StrUtil.isNotEmpty(e.getProvince())).collect(Collectors.groupingBy(SysSchool::getProvince));
        } else if ("2".equals(type)) {
            // 按主管部门分类
            schoolMap = schoolList.stream().filter(e -> StrUtil.isNotEmpty(e.getManage())).collect(Collectors.groupingBy(SysSchool::getManage));
        } else if ("3".equals(type)) {
            // 按层次分类
            schoolMap = schoolList.stream().filter(e -> StrUtil.isNotEmpty(e.getType())).collect(Collectors.groupingBy(SysSchool::getType));
        }

        schoolMap.forEach((key, value) -> {
            LinkedHashMap<String, Object> item = new LinkedHashMap<String, Object>() {
                {
                    put("name", key);
                    put("data", value);
                }
            };
            result.add(item);
        });
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
