package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ApplyBillInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 志愿申请 mapper层
 *
 * @author FanK
 */
public interface ApplyBillInfoMapper extends BaseMapper<ApplyBillInfo> {

    /**
     * 分页获取志愿申请信息
     *
     * @param page          分页对象
     * @param applyBillInfo 志愿申请信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectApplyBillPage(Page<ApplyBillInfo> page, @Param("applyBillInfo") ApplyBillInfo applyBillInfo);

    /**
     * 本月申请单信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<ApplyBillInfo> selectOrderByMonth(@Param("schoolId") Integer schoolId);

    /**
     * 本年申请单信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<ApplyBillInfo> selectOrderByYear(@Param("schoolId") Integer schoolId);

    /**
     * 十天内申请单数量统计
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumWithinDays(@Param("schoolId") Integer schoolId);

    /**
     * 十天内申请单统计
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderPriceWithinDays(@Param("schoolId") Integer schoolId);
}
