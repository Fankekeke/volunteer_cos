package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ApplyBillInfo;
import cc.mrbird.febs.cos.service.IApplyBillInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 志愿申请 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/apply-bill-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplyBillInfoController {

    private final IApplyBillInfoService applyBillInfoService;

    /**
     * 分页获取志愿申请信息
     *
     * @param page          分页对象
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ApplyBillInfo> page, ApplyBillInfo applyBillInfo) {
        return R.ok(applyBillInfoService.selectApplyBillPage(page, applyBillInfo));
    }

    /**
     * 查询志愿申请信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(applyBillInfoService.getById(id));
    }

    /**
     * 查询志愿申请信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(applyBillInfoService.list());
    }

    /**
     * 新增志愿申请信息
     *
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    @PostMapping
    public R save(ApplyBillInfo applyBillInfo) {
        return R.ok(applyBillInfoService.save(applyBillInfo));
    }

    /**
     * 修改志愿申请信息
     *
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    @PutMapping
    public R edit(ApplyBillInfo applyBillInfo) {
        return R.ok(applyBillInfoService.updateById(applyBillInfo));
    }

    /**
     * 删除志愿申请信息
     *
     * @param ids ids
     * @return 志愿申请信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(applyBillInfoService.removeByIds(ids));
    }
}
