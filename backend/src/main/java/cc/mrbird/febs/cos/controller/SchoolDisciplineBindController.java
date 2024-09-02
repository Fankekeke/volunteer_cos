package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SchoolDisciplineBind;
import cc.mrbird.febs.cos.entity.SysSchool;
import cc.mrbird.febs.cos.service.ISchoolDisciplineBindService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学校专业绑定 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/school-discipline-bind")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchoolDisciplineBindController {

    private final ISchoolDisciplineBindService schoolDisciplineBindService;

    private final ISysSchoolService sysSchoolService;

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param schoolDisciplineBind 学校专业绑定信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SchoolDisciplineBind> page, SchoolDisciplineBind schoolDisciplineBind) {
        return R.ok(schoolDisciplineBindService.selectDisciplineBindPage(page, schoolDisciplineBind));
    }

    /**
     * 根据学校ID获取绑定信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    @GetMapping("/selectBindBySchool")
    public R selectBindBySchool(Integer schoolId) {
        return R.ok(schoolDisciplineBindService.selectBindBySchool(schoolId));
    }

    /**
     * 查询学校专业绑定信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(schoolDisciplineBindService.getById(id));
    }

    /**
     * 查询学校专业绑定信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(schoolDisciplineBindService.list());
    }

    /**
     * 新增学校专业绑定信息
     *
     * @param schoolDisciplineBind 学校专业绑定信息
     * @return 结果
     */
    @PostMapping
    public R save(SchoolDisciplineBind schoolDisciplineBind) {
        if (schoolDisciplineBind.getSchoolIdFlag() != null) {
            SysSchool school = sysSchoolService.getOne(Wrappers.<SysSchool>lambdaQuery().eq(SysSchool::getUserId, schoolDisciplineBind.getSchoolIdFlag()));
            if (school != null) {
                schoolDisciplineBind.setSchoolId(school.getId());
            }
        }
        return R.ok(schoolDisciplineBindService.save(schoolDisciplineBind));
//        // 获取绑定信息
//        List<SchoolDisciplineBind> bindList = schoolDisciplineBind.getBinds();
//        // 删除旧数据
//        schoolDisciplineBindService.remove(Wrappers.<SchoolDisciplineBind>lambdaQuery().eq(SchoolDisciplineBind::getSchoolId, schoolDisciplineBind.getSchoolId()));
//        // 重新绑定
//        for (SchoolDisciplineBind disciplineBind : bindList) {
//            disciplineBind.setSchoolId(schoolDisciplineBind.getSchoolId());
//        }
//        return R.ok(schoolDisciplineBindService.saveBatch(bindList));
    }

    /**
     * 修改学校专业绑定信息
     *
     * @param schoolDisciplineBind 学校专业绑定信息
     * @return 结果
     */
    @PutMapping
    public R edit(SchoolDisciplineBind schoolDisciplineBind) {
        return R.ok(schoolDisciplineBindService.updateById(schoolDisciplineBind));
    }

    /**
     * 删除学校专业绑定信息
     *
     * @param ids ids
     * @return 学校专业绑定信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(schoolDisciplineBindService.removeByIds(ids));
    }
}
