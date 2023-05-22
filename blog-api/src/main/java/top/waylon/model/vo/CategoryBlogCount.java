package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 分类和博客数量
 */
@NoArgsConstructor
@Data
public class CategoryBlogCount {
	private Long id;
	private String name;//分类名
	private Integer value;//分类下博客数量
}
