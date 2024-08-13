package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 全国学校数据 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/sys-school")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSchoolController {

    private final ISysSchoolService sysSchoolService;

    /**
     * 分页获取学校信息
     *
     * @param page      分页对象
     * @param sysSchool 学校信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SysSchool> page, SysSchool sysSchool) {
        return R.ok(sysSchoolService.selectSchoolPage(page, sysSchool));
    }

    /**
     * 查询学校信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(sysSchoolService.getById(id));
    }

    /**
     * 查询学校信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(sysSchoolService.list());
    }

    /**
     * 新增学校信息
     *
     * @param sysSchool 学校信息
     * @return 结果
     */
    @PostMapping
    public R save(SysSchool sysSchool) {
        return R.ok(sysSchoolService.save(sysSchool));
    }

    /**
     * 修改学校信息
     *
     * @param sysSchool 学校信息
     * @return 结果
     */
    @PutMapping
    public R edit(SysSchool sysSchool) {
        return R.ok(sysSchoolService.updateById(sysSchool));
    }

    /**
     * 删除学校信息
     *
     * @param ids ids
     * @return 学校信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(sysSchoolService.removeByIds(ids));
    }
}
