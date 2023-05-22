package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 友链VO
 */
@NoArgsConstructor
@Data
public class Friend {
	private String nickname;//昵称
	private String description;//描述
	private String website;//站点
	private String avatar;//头像
}
