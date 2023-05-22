package top.waylon.model.dto;

import lombok.*;

/**
 * 访问日志备注
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitLogRemark {
	/**
	 * 访问内容
	 */
	private String content;

	/**
	 * 备注
	 */
	private String remark;
}
