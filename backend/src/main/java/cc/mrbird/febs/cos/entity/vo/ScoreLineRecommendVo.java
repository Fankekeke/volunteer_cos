package cc.mrbird.febs.cos.entity.vo;

import lombok.Data;

@Data
public class ScoreLineRecommendVo {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 所属学校
     */
    private Integer schoolId;

    /**
     * 所属专业
     */
    private String disciplineCode;

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

    /**
     * 学校名称
     */
    private String schoolName;
    private String address;
    private String city;
    private String province;
    private String manage;
    private String level;
    private String disciplineName;
    private String employment;
}
