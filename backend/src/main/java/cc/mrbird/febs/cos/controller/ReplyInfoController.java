package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ReplyInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IReplyInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 学校评论管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/reply-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyInfoController {

    private final IReplyInfoService replyInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取学校评论信息
     *
     * @param page      分页对象
     * @param replyInfo 学校评论信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ReplyInfo> page, ReplyInfo replyInfo) {
        return R.ok(replyInfoService.selectReplyPage(page, replyInfo));
    }

    /**
     * 根据学校ID获取评论信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    @GetMapping("/selectReplyBySchool")
    public R selectReplyBySchool(Integer schoolId) {
        return R.ok(replyInfoService.selectReplyBySchool(schoolId));
    }

    /**
     * 查询学校评论信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(replyInfoService.getById(id));
    }

    /**
     * 查询学校评论信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(replyInfoService.list());
    }

    /**
     * 新增学校评论信息
     *
     * @param replyInfo 学校评论信息
     * @return 结果
     */
    @PostMapping
    public R save(ReplyInfo replyInfo) {
        // 发送时间
        replyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 用户ID设置
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, replyInfo.getUserId()));
        if (userInfo != null) {
            replyInfo.setUserId(userInfo.getId());
        }
        return R.ok(replyInfoService.save(replyInfo));
    }

    /**
     * 修改学校评论信息
     *
     * @param replyInfo 学校评论信息
     * @return 结果
     */
    @PutMapping
    public R edit(ReplyInfo replyInfo) {
        return R.ok(replyInfoService.updateById(replyInfo));
    }

    /**
     * 删除学校评论信息
     *
     * @param ids ids
     * @return 学校评论信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(replyInfoService.removeByIds(ids));
    }
}
