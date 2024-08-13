package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 志愿填报
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserWishInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属学生
     */
    private Integer userId;

    /**
     * 序号
     */
    private Integer indexNo;

    /**
     * 所属学校
     */
    private Integer schoolId;

    /**
     * 所属专业
     */
    private Integer disciplineId;

    /**
     * 志愿状态（0.未提交 1.已提交）
     */
    private String status;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String schoolName;

    @TableField(exist = false)
    private String disciplineName;
}
