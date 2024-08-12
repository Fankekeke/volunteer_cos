package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 专业管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DisciplineInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专业编号
     */
    private String code;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 就业方向
     */
    private String employment;

    /**
     * 专业排名
     */
    private Integer indexNo;


}
