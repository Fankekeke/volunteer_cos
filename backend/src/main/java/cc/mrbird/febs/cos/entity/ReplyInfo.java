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
 * 学校评论管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReplyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评价人
     */
    private Integer userId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 回复用户
     */
    private Integer toUserId;

    /**
     * 回复时间
     */
    private String createDate;

    /**
     * 所属学校
     */
    private Integer schoolId;

    @TableField(exist = false)
    private String schoolName;

    @TableField(exist = false)
    private String userName;
}
