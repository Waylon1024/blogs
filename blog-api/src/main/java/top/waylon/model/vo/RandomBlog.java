package top.waylon.model.vo;

import lombok.*;

import java.util.Date;

/**
 * @Description: 随机博客
 */
@NoArgsConstructor
@Data
public class RandomBlog {
	private Long id;
	private String title;//文章标题
	private String firstPicture;//文章首图，用于推荐文章展示
	private Date createTime;//创建时间
	private String password;//文章密码
	private Boolean privacy;//是否私密文章
}
