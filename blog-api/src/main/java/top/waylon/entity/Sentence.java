package top.waylon.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import top.waylon.config.StringEmptyToNullDeserializer;

import java.util.Date;

/**
 * @author WayLon
 * @create 2023-05-14 15:20
 * 备注:
 */
@NoArgsConstructor
@Data
public class Sentence {
    private Integer id;

    private String creator = "Waylon";

    private Integer type;

    @JsonDeserialize(using = StringEmptyToNullDeserializer.class)
    private String content;

    @JsonDeserialize(using = StringEmptyToNullDeserializer.class)
    private String source;

    private Date createTime;

    private Date updateTime;
}
