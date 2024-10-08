package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.DisciplineInfo;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.service.IDisciplineInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 专业管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/discipline-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DisciplineInfoController {

    private final IDisciplineInfoService disciplineInfoService;

    /**
     * 分页获取专业信息
     *
     * @param page           分页对象
     * @param disciplineInfo 专业信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<DisciplineInfo> page, DisciplineInfo disciplineInfo) {
        return R.ok(disciplineInfoService.selectDisciplinePage(page, disciplineInfo));
    }

    /**
     * 获取专业类型
     *
     * @return 结果
     */
    @GetMapping("/selectDisciplineTopList")
    public R selectDisciplineTopList() {
        return R.ok(disciplineInfoService.list(Wrappers.<DisciplineInfo>lambdaQuery().eq(DisciplineInfo::getType, "1")));
    }

    /**
     * 查询学校信息列表
     *
     * @return 结果
     */
    @GetMapping("/listLikeByKey/{name}")
    public R listLikeByKey(@PathVariable("name") String name) {
        return R.ok(disciplineInfoService.list(Wrappers.<DisciplineInfo>lambdaQuery().eq(DisciplineInfo::getType, "2").like(StrUtil.isNotEmpty(name), DisciplineInfo::getName, name).last("LIMIT 10")));
    }

    /**
     * 查询专业信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(disciplineInfoService.getById(id));
    }

    /**
     * 查询专业信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(disciplineInfoService.list());
    }

    /**
     * 新增专业信息
     *
     * @param disciplineInfo 专业信息
     * @return 结果
     */
    @PostMapping
    public R save(DisciplineInfo disciplineInfo) {
        // 专业编号
        disciplineInfo.setCode("DIS" + UUID.fastUUID());
        disciplineInfo.setDelFlag("0");
        // 创建时间
        disciplineInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(disciplineInfoService.save(disciplineInfo));
    }

    /**
     * 修改专业信息
     *
     * @param disciplineInfo 专业信息
     * @return 结果
     */
    @PutMapping
    public R edit(DisciplineInfo disciplineInfo) {
        return R.ok(disciplineInfoService.updateById(disciplineInfo));
    }

    /**
     * 删除专业信息
     *
     * @param ids ids
     * @return 专业信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(disciplineInfoService.update(Wrappers.<DisciplineInfo>lambdaUpdate().set(DisciplineInfo::getDelFlag, "1").in(DisciplineInfo::getId, ids)));
    }
}
