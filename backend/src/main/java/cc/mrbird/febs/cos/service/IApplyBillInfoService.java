package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ApplyBillInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 志愿申请 service层
 *
 * @author FanK
 */
public interface IApplyBillInfoService extends IService<ApplyBillInfo> {

    /**
     * 分页获取志愿申请信息
     *
     * @param page          分页对象
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectApplyBillPage(Page<ApplyBillInfo> page, ApplyBillInfo applyBillInfo);
}
