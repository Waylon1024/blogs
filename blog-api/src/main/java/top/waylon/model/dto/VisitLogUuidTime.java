package top.waylon.model.dto;

import lombok.*;

import java.util.Date;

/**
 * @Description: 访客更新DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitLogUuidTime {
	private String uuid;
	private Date time;
	private Integer pv;
}
