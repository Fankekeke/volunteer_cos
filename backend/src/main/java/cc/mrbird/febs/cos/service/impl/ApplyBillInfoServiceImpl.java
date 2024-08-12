package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ApplyBillInfo;
import cc.mrbird.febs.cos.dao.ApplyBillInfoMapper;
import cc.mrbird.febs.cos.service.IApplyBillInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 志愿申请 实现层
 *
 * @author FanK
 */
@Service
public class ApplyBillInfoServiceImpl extends ServiceImpl<ApplyBillInfoMapper, ApplyBillInfo> implements IApplyBillInfoService {

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
}
