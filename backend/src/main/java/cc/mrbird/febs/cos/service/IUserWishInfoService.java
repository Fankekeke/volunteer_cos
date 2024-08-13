package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.UserWishInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 志愿填报 service层
 *
 * @author FanK
 */
public interface IUserWishInfoService extends IService<UserWishInfo> {

    /**
     * 分页获取志愿填报信息
     *
     * @param page         分页对象
     * @param userWishInfo 志愿填报信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserWishPage(Page<UserWishInfo> page, UserWishInfo userWishInfo);
}
