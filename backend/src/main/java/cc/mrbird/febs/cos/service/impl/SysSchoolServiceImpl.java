package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.utils.SpaceUtil;
import cc.mrbird.febs.cos.dao.ApplyBillInfoMapper;
import cc.mrbird.febs.cos.dao.DisciplineInfoMapper;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.SysSchoolMapper;
import cc.mrbird.febs.cos.service.IApplyBillInfoService;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.ISysSchoolService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 学校信息 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSchoolServiceImpl extends ServiceImpl<SysSchoolMapper, SysSchool> implements ISysSchoolService {

    private final IApplyBillInfoService applyBillInfoService;

    private final ApplyBillInfoMapper applyBillInfoMapper;

    private final IBulletinInfoService bulletinInfoService;

    private final UserInfoMapper userInfoMapper;

    private final SysSchoolMapper sysSchoolMapper;

    private final DisciplineInfoMapper disciplineInfoMapper;

    /**
     * 分页获取学校信息
     *
     * @param page      分页对象
     * @param sysSchool 学校信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectSchoolPage(Page<SysSchool> page, SysSchool sysSchool) {
        return baseMapper.selectSchoolPage(page, sysSchool);
    }

    /**
     * 获取首页统计数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("userNum", 0);
                put("schoolNum", 0);
                put("disciplineNum", 0);
                put("billNum", 0);
            }
        };


        result.put("userNum", userInfoMapper.selectCount(Wrappers.<UserInfo>lambdaQuery()));
        result.put("schoolNum", sysSchoolMapper.selectCount(Wrappers.<SysSchool>lambdaQuery()));
        result.put("disciplineNum", disciplineInfoMapper.selectCount(Wrappers.<DisciplineInfo>lambdaQuery()));
        result.put("billNum", applyBillInfoService.count());

        // 本月申请数量
        List<ApplyBillInfo> orderMonthList = applyBillInfoMapper.selectOrderByMonth(null);
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderMonthList) ? 0 : orderMonthList.size());
        long passMonthNum = orderMonthList.stream().map(ApplyBillInfo::getStatus).filter("3"::equals).count();
        // 本月审核通过
        result.put("passMonthNum", passMonthNum);

        // 本年申请数量
        List<ApplyBillInfo> orderYearList = applyBillInfoMapper.selectOrderByYear(null);
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年审核通过
        long passYearNum = orderYearList.stream().map(ApplyBillInfo::getStatus).filter("3"::equals).count();
        result.put("passYearNum", passYearNum);

        // 近十天申请
        result.put("orderNumDayList", applyBillInfoMapper.selectOrderNumWithinDays(null));
        // 近十天申请通过
        result.put("priceDayList", applyBillInfoMapper.selectOrderPriceWithinDays(null));
        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        return result;
    }

    /**
     * 逆地址解析
     */
    @Override
    public void addressResolve() {
        // 获取所有学校信息
        List<SysSchool> hospitalInfoList = this.list(Wrappers.<SysSchool>lambdaQuery().isNotNull(SysSchool::getAddress));
        if (CollectionUtil.isEmpty(hospitalInfoList)) {
            return;
        }
        // 待更新的医院数据
        List<SysSchool> toupList = new ArrayList<>();
        for (SysSchool hospitalInfo : hospitalInfoList) {
            String point = SpaceUtil.getCoordinate(hospitalInfo.getAddress());
            if (StrUtil.isEmpty(point)) {
                continue;
            }
            List<String> pointList = StrUtil.split(point, ',');
            hospitalInfo.setLongitude(new BigDecimal(pointList.get(0)));
            hospitalInfo.setLatitude(new BigDecimal(pointList.get(1)));
            toupList.add(hospitalInfo);
        }
        this.updateBatchById(toupList);
    }
}
