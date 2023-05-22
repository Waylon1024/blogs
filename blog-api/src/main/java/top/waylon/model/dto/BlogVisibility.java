package top.waylon.model.dto;

import lombok.*;

/**
 * @Description: 博客可见性DTO
 */
@NoArgsConstructor
@Data
public class BlogVisibility {
	private Boolean appreciation;//赞赏开关
	private Boolean recommend;//推荐开关
	private Boolean commentEnabled;//评论开关
	private Boolean top;//是否置顶
	private Boolean published;//公开或私密
	private String password;//密码保护
}
