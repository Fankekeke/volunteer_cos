package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
    private LocalDateTime createDate;

    /**
     * 用户确认时间
     */
    private LocalDateTime userConfirmDate;

    /**
     * 学校确认时间
     */
    private LocalDateTime schoolConfirmDate;


}
