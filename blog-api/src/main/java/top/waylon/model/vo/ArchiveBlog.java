package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 归档页面博客简要信息
 */
@NoArgsConstructor
@Data
public class ArchiveBlog {
	private Long id;
	private String title;
	private String day;
	private String password;
	private Boolean privacy;
}
