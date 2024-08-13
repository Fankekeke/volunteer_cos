package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ReplyInfo;
import cc.mrbird.febs.cos.dao.ReplyInfoMapper;
import cc.mrbird.febs.cos.service.IReplyInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 学校评论管理 实现层
 *
 * @author FanK
 */
@Service
public class ReplyInfoServiceImpl extends ServiceImpl<ReplyInfoMapper, ReplyInfo> implements IReplyInfoService {

    /**
     * 分页获取学校评论信息
     *
     * @param page      分页对象
     * @param replyInfo 学校评论信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectReplyPage(Page<ReplyInfo> page, ReplyInfo replyInfo) {
        return baseMapper.selectReplyPage(page, replyInfo);
    }

    /**
     * 根据学校ID获取评论信息
     *
     * @param schoolId 学校ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectReplyBySchool(Integer schoolId) {
        return baseMapper.selectReplyBySchool(schoolId);
    }
}
