package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.Professional;
import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.service.IProfessionalService;
import cc.mrbird.febs.cos.service.IScoreLineInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分数线 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/score-line-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScoreLineInfoController {

    private final IScoreLineInfoService scoreLineInfoService;

    private final IProfessionalService professionalService;

    /**
     * 分页获取分数线信息
     *
     * @param page          分页对象
     * @param scoreLineInfo 分数线信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) {
        return R.ok(scoreLineInfoService.selectScoreLinePage(page, scoreLineInfo));
    }

    /**
     * 根据学校ID获取招生信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    @GetMapping("/selectScoreLineBySchoolId/{schoolId}")
    public R selectScoreLineBySchoolId(@PathVariable("schoolId") Integer schoolId) {
        return R.ok(scoreLineInfoService.selectScoreLineBySchoolId(schoolId));
    }

    @Async
    @GetMapping("/generateScoreLine")
    public R generateScoreLine() {
        List<Professional> professionalList = professionalService.list();
//        Map<Integer, List<Professional>> professionalMap = professionalList.stream().collect(Collectors.groupingBy(Professional::getSchoolId));

        List<ScoreLineInfo> addList = new ArrayList<>();

        for (Professional professional : professionalList) {
            ScoreLineInfo scoreLineInfo = new ScoreLineInfo();
            scoreLineInfo.setSchoolId(professional.getSchoolId());
            scoreLineInfo.setDisciplineCode(professional.getDisciplineCode());
            scoreLineInfo.setType("1");
            scoreLineInfo.setScore(350);
            scoreLineInfo.setTemplateFlag("0");
            scoreLineInfo.setYear("2024");
            scoreLineInfo.setAdmissions(50);

            ScoreLineInfo scoreLineInfo1 = new ScoreLineInfo();
            scoreLineInfo1.setSchoolId(professional.getSchoolId());
            scoreLineInfo1.setDisciplineCode(professional.getDisciplineCode());
            scoreLineInfo1.setType("2");
            scoreLineInfo1.setScore(350);
            scoreLineInfo1.setTemplateFlag("0");
            scoreLineInfo1.setYear("2024");
            scoreLineInfo1.setAdmissions(50);
            addList.add(scoreLineInfo);
            addList.add(scoreLineInfo1);
        }
        scoreLineInfoService.saveBatch(addList);
        return R.ok(true);
    }

    /**
     * 校院统计
     *
     * @return 结果
     */
    @GetMapping("/selectSchoolRate")
    public R selectSchoolRate() {
        return R.ok(scoreLineInfoService.selectSchoolRate());
    }

    /**
     * 校院统计
     *
     * @return 结果
     */
    @GetMapping("/selectSchoolRate/type/{type}")
    public R selectSchoolRateByType(@PathVariable("type") String type) {
        return R.ok(scoreLineInfoService.selectSchoolRateByType(type));
    }

    /**
     * 招生统计
     *
     * @param year 年份
     * @return 结果
     */
    public R selectAdmissionsRate(Integer year) {
        return R.ok();
    }

    /**
     * 获取分数线地图信息
     *
     * @param year 年份
     * @return 结果
     */
    @GetMapping("/selectScoreMap")
    public R selectScoreMap(Integer year) {
        return R.ok(scoreLineInfoService.selectScoreMap(year));
    }

    /**
     * 获取推荐学校
     *
     * @param page          分页对象
     * @param scoreLineInfo 参数
     * @return 结果
     */
    @GetMapping("/selectRecommendSchool")
    public R selectRecommendSchool(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) throws FebsException {
        return R.ok(scoreLineInfoService.selectRecommendSchool(page, scoreLineInfo));
    }

    /**
     * 根据专业获取推荐学校
     *
     * @param scoreLineInfo 参数
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/selectRecommendByMaior")
    public R selectRecommendByMaior(ScoreLineInfo scoreLineInfo) throws FebsException {
        return R.ok(scoreLineInfoService.selectRecommendByMaior(scoreLineInfo));
    }

    /**
     * 根据省份获取推荐学校
     * @param scoreLineInfo 参数
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/selectRecommendProvice")
    public R selectRecommendProvinces(ScoreLineInfo scoreLineInfo) throws FebsException {
        return R.ok(scoreLineInfoService.selectRecommendProvinces(scoreLineInfo));
    }

    /**
     * 根据学校获取分数线
     *
     * @param schoolId 学校ID
     * @param year     年份
     * @return 结果
     */
    @GetMapping("/selectScoreLineBySchool")
    public R selectScoreLineBySchool(Integer schoolId, String year) {
        return R.ok(scoreLineInfoService.selectScoreLineBySchool(schoolId, year));
    }

    /**
     * 查询分数线信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(scoreLineInfoService.getById(id));
    }

    /**
     * 查询分数线信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(scoreLineInfoService.list());
    }

    /**
     * 新增分数线信息
     *
     * @param scoreLineInfo 分数线信息
     * @return 结果
     */
    @PostMapping
    public R save(ScoreLineInfo scoreLineInfo) {
        return R.ok(scoreLineInfoService.save(scoreLineInfo));
    }

    /**
     * 修改分数线信息
     *
     * @param scoreLineInfo 分数线信息
     * @return 结果
     */
    @PutMapping
    public R edit(ScoreLineInfo scoreLineInfo) {
        return R.ok(scoreLineInfoService.updateById(scoreLineInfo));
    }

    /**
     * 删除分数线信息
     *
     * @param ids ids
     * @return 分数线信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(scoreLineInfoService.removeByIds(ids));
    }
}
