package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.UserWishDiscipline;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 学生志愿专业 service层
 *
 * @author FanK
 */
public interface IUserWishDisciplineService extends IService<UserWishDiscipline> {

    /**
     * 分页获取学生志愿专业信息
     *
     * @param page               分页对象
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserWishDisciplinePage(Page<UserWishDiscipline> page, UserWishDiscipline userWishDiscipline);
}
