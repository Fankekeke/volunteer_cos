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
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) {
        return R.ok(scoreLineInfoService.selectScoreLinePage(page, scoreLineInfo));
    }

    /**
     * 查询学校专业绑定信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(scoreLineInfoService.getById(id));
    }

    /**
     * 查询学校专业绑定信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(scoreLineInfoService.list());
    }

    /**
     * 新增学校专业绑定信息
     *
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    @PostMapping
    public R save(ScoreLineInfo scoreLineInfo) {
        return R.ok(scoreLineInfoService.save(scoreLineInfo));
    }

    /**
     * 修改学校专业绑定信息
     *
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    @PutMapping
    public R edit(ScoreLineInfo scoreLineInfo) {
        return R.ok(scoreLineInfoService.updateById(scoreLineInfo));
    }

    /**
     * 删除学校专业绑定信息
     *
     * @param ids ids
     * @return 学校专业绑定信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(scoreLineInfoService.removeByIds(ids));
    }
}
