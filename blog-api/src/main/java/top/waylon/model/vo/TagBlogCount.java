package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 标签和博客数量
 */
@NoArgsConstructor
@Data
public class TagBlogCount {
	private Long id;
	private String name;//标签名
	private Integer value;//标签下博客数量
}
