package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.entity.vo.ScoreLineRecommendVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分数线 service层
 *
 * @author FanK
 */
public interface IScoreLineInfoService extends IService<ScoreLineInfo> {

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page          分页对象
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectScoreLinePage(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo);

    /**
     * 根据学校ID获取招生信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectScoreLineBySchoolId(Integer schoolId);

    /**
     * 根据学校获取分数线
     *
     * @param schoolId 学校ID
     * @param year     年份
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectScoreLineBySchool(Integer schoolId, String year);

    /**
     * 获取分数线地图信息
     *
     * @param year 年份
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectScoreMap(Integer year);

    /**
     * 获取推荐学校
     *
     * @param page          分页对象
     * @param scoreLineInfo 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecommendSchool(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) throws FebsException;

    /**
     * 根据专业获取推荐学校
     *
     * @param scoreLineInfo 参数
     * @return 结果
     * @throws FebsException 异常
     */
    List<LinkedHashMap<String, Object>> selectRecommendByMaior(ScoreLineInfo scoreLineInfo) throws FebsException;

    /**
     * 根据省份获取推荐学校
     * @param scoreLineInfo 参数
     * @return 结果
     * @throws FebsException 异常
     */
    List<LinkedHashMap<String, Object>> selectRecommendProvinces(ScoreLineInfo scoreLineInfo) throws FebsException;

    /**
     * 校院统计
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> selectSchoolRate();

    /**
     * 校院统计
     *
     * @param type 类型
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectSchoolRateByType(String type);

    /**
     * 招生统计
     *
     * @param year 年份
     * @return 结果
     */
    LinkedHashMap<String, Object> selectAdmissionsRate(Integer year);
}
