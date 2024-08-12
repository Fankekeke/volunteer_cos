package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分数线
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScoreLineInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属学校
     */
    private Integer schoolId;

    /**
     * 所属专业
     */
    private Integer disciplineId;

    /**
     * 类型（1.文科 2.理科）
     */
    private String type;

    /**
     * 招生分数
     */
    private Integer score;

    /**
     * 是否为模板（0.否 1.是）
     */
    private String templateFlag;

    /**
     * 所属年份
     */
    private String year;

    /**
     * 招生人数
     */
    private Integer admissions;


}
