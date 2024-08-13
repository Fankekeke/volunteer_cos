package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.entity.UserWishInfo;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cc.mrbird.febs.cos.service.IUserWishInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 志愿填报 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/user-wish-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWishInfoController {

    private final IUserWishInfoService userWishInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取志愿填报信息
     *
     * @param page         分页对象
     * @param userWishInfo 志愿填报信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserWishInfo> page, UserWishInfo userWishInfo) {
        return R.ok(userWishInfoService.selectUserWishPage(page, userWishInfo));
    }

    /**
     * 查询志愿填报信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userWishInfoService.getById(id));
    }

    /**
     * 查询志愿填报信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userWishInfoService.list());
    }

    /**
     * 新增志愿填报信息
     *
     * @param userWishInfo 志愿填报信息
     * @return 结果
     */
    @PostMapping
    public R save(UserWishInfo userWishInfo) {
        // 获取学生信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userWishInfo.getUserId()));
        if (userInfo != null) {
            userWishInfo.setUserId(userInfo.getId());
        }
        return R.ok(userWishInfoService.save(userWishInfo));
    }

    /**
     * 修改志愿填报信息
     *
     * @param userWishInfo 志愿填报信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserWishInfo userWishInfo) {
        return R.ok(userWishInfoService.updateById(userWishInfo));
    }

    /**
     * 删除志愿填报信息
     *
     * @param ids ids
     * @return 志愿填报信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userWishInfoService.removeByIds(ids));
    }
}
