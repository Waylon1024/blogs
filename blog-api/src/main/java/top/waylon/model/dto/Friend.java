package top.waylon.model.dto;

import lombok.*;

/**
 * @Description: 友链DTO
 */
@NoArgsConstructor
@Data
public class Friend {
	private Long id;
	private String nickname;//昵称
	private String description;//描述
	private String website;//站点
	private String avatar;//头像
	private Boolean published;//公开或隐藏
}
