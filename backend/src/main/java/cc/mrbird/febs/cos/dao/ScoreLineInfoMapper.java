package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.entity.vo.ScoreLineRecommendVo;
import cc.mrbird.febs.cos.entity.vo.ScoreLineVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 分数线 mapper层
 *
 * @author FanK
 */
public interface ScoreLineInfoMapper extends BaseMapper<ScoreLineInfo> {

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page          分页对象
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectScoreLinePage(Page<ScoreLineInfo> page, @Param("scoreLineInfo") ScoreLineInfo scoreLineInfo);

    /**
     * 分页获取推荐学校专业绑定信息
     *
     * @param page             分页对象
     * @param scoreLineInfo    学校专业绑定信息
     * @param disciplineIdList 专业ID
     * @param schoolIdList     学校ID
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectScoreLineRecommendPage(Page<ScoreLineInfo> page, @Param("scoreLineInfo") ScoreLineInfo scoreLineInfo, @Param("schoolIdList") List<Integer> schoolIdList, @Param("disciplineIdList") List<Integer> disciplineIdList);

    /**
     * 获取推荐学校专业绑定信息
     *
     * @param scoreLineInfo    学校专业绑定信息
     * @param disciplineIdList 专业ID
     * @param schoolIdList     学校ID
     * @return 结果
     */
    List<ScoreLineRecommendVo> selectScoreLineRecommend(@Param("scoreLineInfo") ScoreLineInfo scoreLineInfo, @Param("schoolIdList") List<Integer> schoolIdList, @Param("disciplineIdList") List<Integer> disciplineIdList);


    /**
     * 根据学校ID获取招生信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectScoreLineBySchoolId(@Param("schoolId") Integer schoolId);

    /**
     * 根据学校获取分数线
     *
     * @param schoolId 学校ID
     * @param year     年份
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectScoreLineBySchool(@Param("schoolId") Integer schoolId, @Param("year") String year);

    /**
     * 根据学校获取分数线
     *
     * @param schoolIds 学校IDS
     * @param year      年份
     * @return 结果
     */
    List<ScoreLineVo> selectScoreLineBySchoolIds(@Param("schoolIds") List<Integer> schoolIds, @Param("year") String year);

    /**
     * 查询默认分数线
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectScoreLineDefault();

    /**
     * 查询默认分数线
     *
     * @return 结果
     */
    List<ScoreLineVo> selectScoreLineDefaultFix();
}
