package top.waylon.entity;

import lombok.*;

/**
 * @Description: 关于我
 * @Author: Naccl
 * @Date: 2020-08-31
 */
@NoArgsConstructor
@Data
public class About {
	private Long id;
	private String nameEn;
	private String nameZh;
	private String value;
}
