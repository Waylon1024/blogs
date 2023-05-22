package top.waylon.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 博客分类
 * @Author: Naccl
 * @Date: 2020-07-26
 */
@NoArgsConstructor
@Data
public class Category {
	private Long id;
	private String name;//分类名称
	private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章
}
