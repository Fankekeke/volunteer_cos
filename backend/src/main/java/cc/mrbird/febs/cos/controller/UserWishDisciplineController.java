package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserWishDiscipline;
import cc.mrbird.febs.cos.service.IUserWishDisciplineService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生志愿专业志愿专业 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/user-wish-discipline")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWishDisciplineController {

    private final IUserWishDisciplineService userWishDisciplineService;

    /**
     * 分页获取学生志愿专业信息
     *
     * @param page               分页对象
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserWishDiscipline> page, UserWishDiscipline userWishDiscipline) {
        return R.ok(userWishDisciplineService.selectUserWishDisciplinePage(page, userWishDiscipline));
    }

    /**
     * 查询学生志愿专业信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userWishDisciplineService.getById(id));
    }

    /**
     * 查询学生志愿专业信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userWishDisciplineService.list());
    }

    /**
     * 新增学生志愿专业信息
     *
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    @PostMapping
    public R save(UserWishDiscipline userWishDiscipline) {
        return R.ok(userWishDisciplineService.save(userWishDiscipline));
    }

    /**
     * 修改学生志愿专业信息
     *
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserWishDiscipline userWishDiscipline) {
        return R.ok(userWishDisciplineService.updateById(userWishDiscipline));
    }

    /**
     * 删除学生志愿专业信息
     *
     * @param ids ids
     * @return 学生志愿专业信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userWishDisciplineService.removeByIds(ids));
    }
}
