package top.waylon.entity;

import lombok.*;

/**
 * @Description: 访问记录
 * @Author: Naccl
 * @Date: 2021-02-23
 */
@NoArgsConstructor
@Data
public class VisitRecord {
	private Long id;
	private Integer pv;//访问量
	private Integer uv;//独立用户
	private String date;//日期"02-23"

	public VisitRecord(Integer pv, Integer uv, String date) {
		this.pv = pv;
		this.uv = uv;
		this.date = date;
	}
}
