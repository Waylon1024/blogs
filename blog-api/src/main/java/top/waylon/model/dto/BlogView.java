package top.waylon.model.dto;

import lombok.*;

/**
 * @Description: 博客浏览量
 */
@NoArgsConstructor
@Data
public class BlogView {
	private Long id;
	private Integer views;
}
