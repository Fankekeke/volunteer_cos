package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SysCity;
import cc.mrbird.febs.cos.service.ISysCityService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 全国城市数据 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/sys-city")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysCityController {

    private final ISysCityService sysCityService;

    /**
     * 分页获取城市信息
     *
     * @param page    分页对象
     * @param sysCity 城市信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SysCity> page, SysCity sysCity) {
        return R.ok();
    }

    /**
     * 查询城市信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(sysCityService.getById(id));
    }

    /**
     * 查询城市信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(sysCityService.list());
    }

    /**
     * 新增城市信息
     *
     * @param sysCity 城市信息
     * @return 结果
     */
    @PostMapping
    public R save(SysCity sysCity) {
        return R.ok(sysCityService.save(sysCity));
    }

    /**
     * 修改城市信息
     *
     * @param sysCity 城市信息
     * @return 结果
     */
    @PutMapping
    public R edit(SysCity sysCity) {
        return R.ok(sysCityService.updateById(sysCity));
    }

    /**
     * 删除城市信息
     *
     * @param ids ids
     * @return 城市信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(sysCityService.removeByIds(ids));
    }
}
