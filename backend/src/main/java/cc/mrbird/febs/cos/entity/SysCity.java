package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 全国城市数据
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysCity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市
     */
    private String name;

    /**
     * 父级
     */
    private Integer parentId;

    private String initial;

    private String initials;

    private String pinyin;

    private String extra;

    private String suffix;

    private String code;

    private String areaCode;

    private Integer orderBy;


}
