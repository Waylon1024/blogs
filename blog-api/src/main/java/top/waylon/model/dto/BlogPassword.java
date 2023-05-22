package top.waylon.model.dto;

import lombok.*;

/**
 * @Description: 受保护文章密码DTO
 */
@NoArgsConstructor
@Data
public class BlogPassword {
	private Long blogId;
	private String password;
}
