package top.waylon.model.dto;

import lombok.*;

/**
 * @Description: UserAgent解析DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAgentDTO {
	private String os;
	private String browser;
}
