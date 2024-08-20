package cc.mrbird.febs.cos.entity.vo;

import cc.mrbird.febs.cos.entity.ScoreLineInfo;
import lombok.Data;

@Data
public class ScoreLineVo extends ScoreLineInfo {

    private String schoolName;

    private String area;

    private String manage;

    private String level;

    private String disciplineName;

    private String employment;

}
