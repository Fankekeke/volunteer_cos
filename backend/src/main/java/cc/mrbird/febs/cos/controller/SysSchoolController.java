package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.Professional;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.IProfessionalService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 学校信息 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/sys-school")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSchoolController {

    private final ISysSchoolService sysSchoolService;

    private final IBulletinInfoService bulletinInfoService;

    private final IProfessionalService professionalService;

    private final UserService userService;

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
     * 获取省份信息
     *
     * @return 结果
     */
    @GetMapping("/queryProvince")
    public R queryProvince() {
        return R.ok(sysSchoolService.queryProvince());
    }

    /**
     * 获取首页统计数据
     *
     * @return 结果
     */
    @GetMapping("/homeData")
    public R homeData() {
        return R.ok(sysSchoolService.homeData());
    }

    /**
     * 逆地址解析
     *
     * @return 结果
     */
    @Async
    @GetMapping("/addressResolve")
    public R addressResolve() {
        sysSchoolService.addressResolve();
        return R.ok(true);
    }


    @Async
    @GetMapping("/test")
    public R test() {
        List<SysSchool> schools = sysSchoolService.list();
        Map<String, List<SysSchool>> schoolMap = schools.stream().collect(Collectors.groupingBy(SysSchool::getName));

        List<Professional> professionalList = professionalService.list();

        for (Professional professional : professionalList) {
            List<SysSchool> confusionList = schoolMap.get(professional.getSchoolName());
            if (CollectionUtil.isEmpty(confusionList)) {
                continue;
            }
            professional.setSchoolId(confusionList.get(0).getId());
        }
        professionalService.updateBatchById(professionalList);
        return R.ok(true);
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
     * 查询学校信息列表
     *
     * @return 结果
     */
    @GetMapping("/listLikeByKey/{name}")
    public R listLikeByKey(@PathVariable("name") String name) {
        return R.ok(sysSchoolService.list(Wrappers.<SysSchool>lambdaQuery().like(StrUtil.isNotEmpty(name), SysSchool::getName, name).last("LIMIT 10")));
    }

    /**
     * 新增学校信息
     *
     * @param sysSchool 学校信息
     * @return 结果
     */
    @PostMapping
    public R save(SysSchool sysSchool) throws Exception {
        sysSchool.setCode("SCH-" + System.currentTimeMillis());
        userService.registSchool(sysSchool.getCode(), "1234qwer", sysSchool);
        return R.ok(true);
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

    /**
     * 获取学校信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    @GetMapping("/selectSchoolBulletin/{schoolId}")
    public R selectSchoolBulletin(@PathVariable("schoolId") Integer schoolId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("school", null);
                put("bulletin", Collections.emptyList());
            }
        };
        SysSchool school = sysSchoolService.getOne(Wrappers.<SysSchool>lambdaQuery().eq(SysSchool::getUserId, schoolId));
        result.put("school", school);

        // 学校信息
        List<BulletinInfo> bulletinList = bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1));
        result.put("bulletin", bulletinList);
        return R.ok(result);
    }
}
