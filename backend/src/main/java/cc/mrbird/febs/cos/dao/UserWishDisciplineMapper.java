package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.UserWishDiscipline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 学生志愿专业 mapper层
 *
 * @author FanK
 */
public interface UserWishDisciplineMapper extends BaseMapper<UserWishDiscipline> {

    /**
     * 分页获取学生志愿专业信息
     *
     * @param page               分页对象
     * @param userWishDiscipline 学生志愿专业信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserWishDisciplinePage(Page<UserWishDiscipline> page, @Param("userWishDiscipline") UserWishDiscipline userWishDiscipline);
}
