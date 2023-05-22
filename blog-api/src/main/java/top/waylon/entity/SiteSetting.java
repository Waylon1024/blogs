package top.waylon.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * @Description: 站点设置
 * @Author: Naccl
 * @Date: 2020-08-09
 */
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteSetting {
	private Long id;
	private String nameEn;
	private String nameZh;
	private String value;
	private Integer type;
}
