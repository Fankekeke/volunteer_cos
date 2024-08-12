package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import cc.mrbird.febs.cos.dao.ScoreLineInfoMapper;
import cc.mrbird.febs.cos.service.IScoreLineInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 分数线 实现层
 *
 * @author FanK
 */
@Service
public class ScoreLineInfoServiceImpl extends ServiceImpl<ScoreLineInfoMapper, ScoreLineInfo> implements IScoreLineInfoService {

    /**
     * 分页获取学校专业绑定信息
     *
     * @param page                 分页对象
     * @param scoreLineInfo 学校专业绑定信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectScoreLinePage(Page<ScoreLineInfo> page, ScoreLineInfo scoreLineInfo) {
        return baseMapper.selectScoreLinePage(page, scoreLineInfo);
    }
}
