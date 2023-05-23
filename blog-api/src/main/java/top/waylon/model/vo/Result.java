package top.waylon.model.vo;

import lombok.*;

/**
 * @Description: 封装响应结果
 */

@NoArgsConstructor
@Data
public class Result {
	private Integer code;
	private String msg;
	private Object data;

	private Result(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		this.data = null;
	}

	private Result(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static Result ok(String msg, Object data) {
		return new Result(200, msg, data);
	}

	public static Result ok(String msg) {
		return new Result(200, msg);
	}

	public static Result error(String msg) {
		return new Result(500, msg);
	}

	public static Result error() {
		return new Result(500, "异常错误1");
	}

	public static Result create(Integer code, String msg, Object data) {
		return new Result(code, msg, data);
	}

	public static Result create(Integer code, String msg) {
		return new Result(code, msg);
	}
}