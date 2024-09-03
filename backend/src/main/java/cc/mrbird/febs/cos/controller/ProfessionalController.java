package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.DisciplineInfo;
import cc.mrbird.febs.cos.entity.Professional;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.service.IDisciplineInfoService;
import cc.mrbird.febs.cos.service.IProfessionalService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学校专业 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/professional")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessionalController {

    private final IProfessionalService professionalService;

    private final IDisciplineInfoService disciplineInfoService;

    private final ISysSchoolService sysSchoolService;

    @Async
    @GetMapping("/test")
    public R test() {
        List<Professional> professionalList = professionalService.list();
        Map<String, List<Professional>> professionalMap = professionalList.stream().collect(Collectors.groupingBy(Professional::getDisciplineType));

        // 待添加的数据
        List<DisciplineInfo> disciplineInfos = new ArrayList<>();
        professionalMap.forEach((key, value) -> {
            DisciplineInfo disciplineInfo = new DisciplineInfo();
            disciplineInfo.setCode("DIS" + UUID.fastUUID().toString());
            disciplineInfo.setName(key);
            disciplineInfo.setType("1");
            disciplineInfo.setDelFlag("0");
            disciplineInfo.setParentCode("0");
            disciplineInfos.add(disciplineInfo);
            for (Professional professional : value) {
                DisciplineInfo disciplineInfo1 = new DisciplineInfo();
                disciplineInfo1.setCode("DIS" + UUID.fastUUID().toString());
                disciplineInfo1.setName(professional.getDisciplineName());
                disciplineInfo1.setType("2");
                disciplineInfo1.setDelFlag("0");
                disciplineInfo1.setParentCode(disciplineInfo.getCode());
                disciplineInfos.add(disciplineInfo1);
            }
        });
        disciplineInfoService.saveBatch(disciplineInfos);
        return R.ok(true);
    }

    @Async
    @GetMapping("/test2")
    public R test2() {
        List<DisciplineInfo> disciplineInfos = disciplineInfoService.list();
        List<DisciplineInfo> disciplineTopList = disciplineInfos.stream().filter(e -> e.getType().equals("1")).collect(Collectors.toList());
        Map<String, List<DisciplineInfo>> disciplineTopMap = disciplineTopList.stream().collect(Collectors.groupingBy(DisciplineInfo::getName));

        Map<String, List<DisciplineInfo>> disciplineMap = disciplineInfos.stream().filter(e -> e.getType().equals("2")).collect(Collectors.groupingBy(DisciplineInfo::getParentCode));

        List<Professional> professionalList = professionalService.list();

        List<Professional> toUpdateList = new ArrayList<>();
        for (Professional professional : professionalList) {
            List<DisciplineInfo> currentTopList = disciplineTopMap.get(professional.getDisciplineType());
            if (CollectionUtil.isEmpty(currentTopList)) {
                continue;
            }
            professional.setDisciplineTypeCode(currentTopList.get(0).getCode());

            List<DisciplineInfo> childList = disciplineMap.get(professional.getDisciplineTypeCode());
            Map<String, List<DisciplineInfo>> childMapList = childList.stream().collect(Collectors.groupingBy(DisciplineInfo::getName));

            List<DisciplineInfo> currentChildList = childMapList.get(professional.getDisciplineName());
            if (CollectionUtil.isEmpty(currentChildList)) {
                toUpdateList.add(professional);
                continue;
            }
            professional.setDisciplineCode(currentChildList.get(0).getCode());
            toUpdateList.add(professional);
        }
        professionalService.updateBatchById(toUpdateList);
        return R.ok(true);
    }

    /**
     * 分页获取学校专业信息
     *
     * @param page         分页对象
     * @param professional 学校专业信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<Professional> page, Professional professional) {
        return R.ok(professionalService.selectProfessionalPage(page, professional));
    }

    /**
     * 查询学校专业信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(professionalService.getById(id));
    }

    /**
     * 查询学校专业信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(professionalService.list());
    }

    /**
     * 新增学校专业信息
     *
     * @param professional 学校专业信息
     * @return 结果
     */
    @PostMapping
    public R save(Professional professional) throws FebsException {
        // 获取学校信息
        SysSchool school = sysSchoolService.getOne(Wrappers.<SysSchool>lambdaQuery().eq(SysSchool::getUserId, professional.getSchoolId()));
        if (school != null) {
            professional.setSchoolId(school.getId());
            professional.setSchoolName(school.getName());

        }

        int total = professionalService.count(Wrappers.<Professional>lambdaQuery().eq(Professional::getSchoolId, professional.getSchoolId()).eq(Professional::getDisciplineCode, professional.getDisciplineCode()));
        if (total > 0) {
            throw new FebsException("此学校已绑定专业");
        }
        // 获取专业类型
        DisciplineInfo disciplineInfo = disciplineInfoService.getOne(Wrappers.<DisciplineInfo>lambdaQuery().eq(DisciplineInfo::getCode, professional.getDisciplineCode()));
        if (disciplineInfo != null) {
            professional.setDisciplineTypeCode(disciplineInfo.getParentCode());
            professional.setDisciplineName(disciplineInfo.getName());
        }
        return R.ok(professionalService.save(professional));
    }

    /**
     * 修改学校专业信息
     *
     * @param professional 学校专业信息
     * @return 结果
     */
    @PutMapping
    public R edit(Professional professional) throws FebsException {
        List<Professional> list = professionalService.list(Wrappers.<Professional>lambdaQuery().eq(Professional::getSchoolId, professional.getSchoolId()).eq(Professional::getDisciplineCode, professional.getDisciplineCode()));
        if (list.size() > 1 || !list.get(0).getId().equals(professional.getId())) {
            throw new FebsException("此学校已绑定专业");
        }
        // 获取专业类型
        DisciplineInfo disciplineInfo = disciplineInfoService.getOne(Wrappers.<DisciplineInfo>lambdaQuery().eq(DisciplineInfo::getCode, professional.getDisciplineCode()));
        if (disciplineInfo != null) {
            professional.setDisciplineTypeCode(disciplineInfo.getParentCode());
            professional.setDisciplineName(disciplineInfo.getName());
        }

        SysSchool sysSchool = sysSchoolService.getById(professional.getSchoolId());
        if (sysSchool != null) {
            professional.setSchoolName(sysSchool.getName());
        }
        return R.ok(professionalService.updateById(professional));
    }

    /**
     * 删除学校专业信息
     *
     * @param ids ids
     * @return 学校专业信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(professionalService.removeByIds(ids));
    }
}
