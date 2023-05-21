package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 评论管理页面按博客title查询评论
 * @Author: Naccl
 * @Date: 2020-08-03
 */
@NoArgsConstructor
@Data
public class BlogIdAndTitle {
	private Long id;
	private String title;
}
