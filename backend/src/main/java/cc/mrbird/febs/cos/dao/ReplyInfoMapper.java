package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ReplyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 学校评论管理 mapper层
 *
 * @author FanK
 */
public interface ReplyInfoMapper extends BaseMapper<ReplyInfo> {

    /**
     * 分页获取学校评论信息
     *
     * @param page      分页对象
     * @param replyInfo 学校评论信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectReplyPage(Page<ReplyInfo> page, @Param("replyInfo") ReplyInfo replyInfo);

    /**
     * 根据学校ID获取评论信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectReplyBySchool(@Param("schoolId") Integer schoolId);

}
