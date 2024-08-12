package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
    private LocalDateTime createDate;

    /**
     * 所属学校
     */
    private Integer schoolId;


}
