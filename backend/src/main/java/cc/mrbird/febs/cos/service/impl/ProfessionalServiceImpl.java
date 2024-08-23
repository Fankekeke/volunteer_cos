package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.Professional;
import cc.mrbird.febs.cos.dao.ProfessionalMapper;
import cc.mrbird.febs.cos.service.IProfessionalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 学校专业 实现层
 *
 * @author FanK
 */
@Service
public class ProfessionalServiceImpl extends ServiceImpl<ProfessionalMapper, Professional> implements IProfessionalService {

}
