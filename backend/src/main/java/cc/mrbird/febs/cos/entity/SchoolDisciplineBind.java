package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学校专业绑定
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SchoolDisciplineBind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 专业ID
     */
    private Integer disciplineId;

    /**
     * 排名
     */
    private Integer indexNo;

    @TableField(exist = false)
    private String schoolName;

    @TableField(exist = false)
    private String disciplineName;

    @TableField(exist = false)
    private String employment;
}
