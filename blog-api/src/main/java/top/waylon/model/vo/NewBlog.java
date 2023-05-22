package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 最新推荐博客
 */
@NoArgsConstructor
@Data
public class NewBlog {
	private Long id;
	private String title;
	private String password;
	private Boolean privacy;
}
