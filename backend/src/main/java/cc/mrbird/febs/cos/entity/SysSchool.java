package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学校信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysSchool implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学校
     */
    private String name;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 水平层次
     */
    private String level;

    /**
     * 办学类别
     */
    private String type;

    /**
     * 办学类型
     */
    private String schoolType;

    /**
     * 985
     */
    private String nineFlag;

    /**
     * 211
     */
    private String twoFlag;

    /**
     * 双一流
     */
    private String doubleFirstClass;

    /**
     * 归属
     */
    private String manage;

    /**
     * 开设专业链接
     */
    private String linkUrl;

    private Integer userId;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}
