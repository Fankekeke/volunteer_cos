package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.entity.ApplyBillInfo;
import cc.mrbird.febs.cos.dao.ApplyBillInfoMapper;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IApplyBillInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 志愿申请 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplyBillInfoServiceImpl extends ServiceImpl<ApplyBillInfoMapper, ApplyBillInfo> implements IApplyBillInfoService {

    private final UserInfoMapper userInfoMapper;

    /**
     * 分页获取志愿申请信息
     *
     * @param page          分页对象
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectApplyBillPage(Page<ApplyBillInfo> page, ApplyBillInfo applyBillInfo) {
        return baseMapper.selectApplyBillPage(page, applyBillInfo);
    }

    /**
     * 添加志愿申请信息
     *
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    @Override
    public boolean addApplyBill(ApplyBillInfo applyBillInfo) {
        // 申请单编号
        applyBillInfo.setCode("BILL-" + System.currentTimeMillis());
        // 创建时间
        applyBillInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 默认状态
        applyBillInfo.setStatus("1");
        // 设置用户信息
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, applyBillInfo.getUserId()));
        if (userInfo != null) {
            applyBillInfo.setUserId(userInfo.getId());
        }
        return this.save(applyBillInfo);
    }
}
