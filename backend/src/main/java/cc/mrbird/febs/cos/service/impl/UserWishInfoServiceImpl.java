package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.UserWishInfo;
import cc.mrbird.febs.cos.dao.UserWishInfoMapper;
import cc.mrbird.febs.cos.service.IUserWishInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 志愿填报 实现层
 *
 * @author FanK
 */
@Service
public class UserWishInfoServiceImpl extends ServiceImpl<UserWishInfoMapper, UserWishInfo> implements IUserWishInfoService {

    /**
     * 分页获取志愿填报信息
     *
     * @param page         分页对象
     * @param userWishInfo 志愿填报信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserWishPage(Page<UserWishInfo> page, UserWishInfo userWishInfo) {
        return baseMapper.selectUserWishPage(page, userWishInfo);
    }
}
