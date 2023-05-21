package top.naccl.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author WayLon
 * @create 2023-05-14 15:20
 * 备注:
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Sentence {
    private Integer id;
    private String creator = "Waylon";
    private Integer type;
    private String content;
    private String source;
    private Date createTime;
    private Date updateTime;
}
