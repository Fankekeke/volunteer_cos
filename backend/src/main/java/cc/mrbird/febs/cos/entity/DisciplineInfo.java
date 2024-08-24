package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 专业编号
     */
    private String code;

    /**
     * 父级编号
     */
    private String parentCode;

    /**
     * 类型（1.专业类型 2.专业名称）
     */
    private String type;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 创建时间
     */
    private String createDate;

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

    @TableField(exist = false)
    private List<DisciplineInfo> children;
}
