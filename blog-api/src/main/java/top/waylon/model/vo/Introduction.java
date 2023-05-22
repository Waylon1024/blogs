package top.waylon.model.vo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 侧边栏资料卡
 */
@NoArgsConstructor
@Data
public class Introduction {
	private String avatar;
	private String name;
	private String github;
	private String telegram;
	private String qq;
	private String bilibili;
	private String netease;
	private String email;

	private List<String> rollText = new ArrayList<>();
	private List<Favorite> favorites = new ArrayList<>();

}
