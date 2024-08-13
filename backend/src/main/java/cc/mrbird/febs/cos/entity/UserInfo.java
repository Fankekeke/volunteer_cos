package cc.mrbird.febs.cos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学生信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生编号
     */
    private String code;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别（1.男 2.女）
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 类型（1.文科 2.理科）
     */
    private String type;

    /**
     * 头像
     */
    private String images;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 所属账户
     */
    private Integer userId;


}
