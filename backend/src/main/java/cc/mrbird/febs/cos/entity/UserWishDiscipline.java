package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
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
     * 所属用户
     */
    private Integer userId;

    /**
     * 专业ID
     */
    private Integer disciplineId;


}
