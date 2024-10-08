package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.Professional;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.entity.UserWishDiscipline;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cc.mrbird.febs.cos.service.IUserWishDisciplineService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生志愿专业 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/user-wish-discipline")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWishDisciplineController {

    private final IUserWishDisciplineService userWishDisciplineService;

    private final IUserInfoService userInfoService;

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
    public R save(UserWishDiscipline userWishDiscipline) throws FebsException {
        // 获取学生信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userWishDiscipline.getUserId()));
        if (userInfo != null) {
            userWishDiscipline.setUserId(userInfo.getId());
        }
        // 校验是否已经绑定
        int count = userWishDisciplineService.count(Wrappers.<UserWishDiscipline>lambdaQuery().eq(UserWishDiscipline::getUserId, userWishDiscipline.getUserId())
                .eq(UserWishDiscipline::getDisciplineId, userWishDiscipline.getDisciplineId()));
        if (count > 0) {
            throw new FebsException("已填报此专业");
        }
        return R.ok(userWishDisciplineService.save(userWishDiscipline));
//        List<UserWishDiscipline> disciplineList = userWishDiscipline.getDisciplineList();
//        // 删除旧数据
//        userWishDisciplineService.remove(Wrappers.<UserWishDiscipline>lambdaQuery().eq(UserWishDiscipline::getUserId, userWishDiscipline.getUserId()));
//        for (UserWishDiscipline wishDiscipline : disciplineList) {
//            wishDiscipline.setUserId(userWishDiscipline.getUserId());
//        }
//        return R.ok(userWishDisciplineService.saveBatch(disciplineList));
    }

    /**
     * 修改学生志愿专业信息
     *
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserWishDiscipline userWishDiscipline) throws FebsException {
        List<UserWishDiscipline> list = userWishDisciplineService.list(Wrappers.<UserWishDiscipline>lambdaQuery().eq(UserWishDiscipline::getUserId, userWishDiscipline.getUserId()).eq(UserWishDiscipline::getDisciplineId, userWishDiscipline.getDisciplineId()));

        if (CollectionUtil.isNotEmpty(list) && (list.size() > 1 || !list.get(0).getId().equals(userWishDiscipline.getId()))) {
            throw new FebsException("已填报此专业");
        }
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
