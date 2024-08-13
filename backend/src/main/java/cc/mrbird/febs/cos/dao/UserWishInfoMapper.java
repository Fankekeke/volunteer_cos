package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.UserWishInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 志愿填报 mapper层
 *
 * @author FanK
 */
public interface UserWishInfoMapper extends BaseMapper<UserWishInfo> {

    /**
     * 分页获取志愿填报信息
     *
     * @param page         分页对象
     * @param userWishInfo 志愿填报信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserWishPage(Page<UserWishInfo> page, @Param("userWishInfo") UserWishInfo userWishInfo);
}
