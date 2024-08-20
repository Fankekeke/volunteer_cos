package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.service.IScoreLineInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
