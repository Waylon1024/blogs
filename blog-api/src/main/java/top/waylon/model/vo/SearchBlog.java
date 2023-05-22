package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 关键字搜索博客
 */
@NoArgsConstructor
@Data
public class SearchBlog {
	private Long id;
	private String title;
	private String content;
}
