package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.UserWishDiscipline;
import cc.mrbird.febs.cos.dao.UserWishDisciplineMapper;
import cc.mrbird.febs.cos.service.IUserWishDisciplineService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 学生志愿专业 实现层
 *
 * @author FanK
 */
@Service
public class UserWishDisciplineServiceImpl extends ServiceImpl<UserWishDisciplineMapper, UserWishDiscipline> implements IUserWishDisciplineService {

    /**
     * 分页获取学生志愿专业信息
     *
     * @param page               分页对象
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserWishDisciplinePage(Page<UserWishDiscipline> page, UserWishDiscipline userWishDiscipline) {
        return baseMapper.selectUserWishDisciplinePage(page, userWishDiscipline);
    }
}
