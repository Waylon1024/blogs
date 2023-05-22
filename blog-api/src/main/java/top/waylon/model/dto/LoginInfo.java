package top.waylon.model.dto;

import lombok.*;

/**
 * @Description: 登录账号密码
 */
@NoArgsConstructor
@Data
public class LoginInfo {
	private String username;
	private String password;
}
