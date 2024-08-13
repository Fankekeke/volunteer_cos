package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 志愿申请
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApplyBillInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属学校
     */
    private Integer schoolId;

    /**
     * 申请单
     */
    private String code;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 状态（1.发送申请 2.用户确认 3.学校确认）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 用户确认时间
     */
    private String userConfirmDate;

    /**
     * 学校确认时间
     */
    private String schoolConfirmDate;

    @TableField(exist = false)
    private String schoolName;

    @TableField(exist = false)
    private String level;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String idCard;
}
