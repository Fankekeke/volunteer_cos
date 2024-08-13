package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学生志愿专业
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserWishDiscipline implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 专业ID
     */
    private Integer disciplineId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String idCard;

    @TableField(exist = false)
    private String disciplineName;

    @TableField(exist = false)
    private List<UserWishDiscipline> disciplineList;
}
