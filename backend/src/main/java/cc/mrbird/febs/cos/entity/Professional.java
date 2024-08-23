package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学校专业
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Professional implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 专业类型
     */
    private String disciplineType;

    /**
     * 专业名称
     */
    private String disciplineName;

    /**
     * 是否特色专业
     */
    private String featureFlag;

    /**
     * 学校ID
     */
    private Integer schoolId;


}
