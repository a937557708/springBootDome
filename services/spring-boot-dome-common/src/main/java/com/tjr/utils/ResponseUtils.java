package com.tjr.utils;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class ResponseUtils {
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	public static ResponseUtils build(Integer status, String msg, Object data) {
		return new ResponseUtils(status, msg, data);
	}

	public static ResponseUtils ok(Object data) {
		return new ResponseUtils(data);
	}

	public static ResponseUtils ok() {
		return new ResponseUtils(null);
	}

	public static ResponseUtils errorMsg(String msg) {
		return new ResponseUtils(500, msg, null);
	}

	public static ResponseUtils errorMap(Object data) {
		return new ResponseUtils(501, "error", data);
	}

	public static ResponseUtils errorTokenMsg(String msg) {
		return new ResponseUtils(502, msg, null);
	}

	public static ResponseUtils errorException(String msg) {
		return new ResponseUtils(555, msg, null);
	}

	public ResponseUtils() {
	}

	public ResponseUtils(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public ResponseUtils(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public static ResponseUtils formatToPojo(String jsonData, Class clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, ResponseUtils.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static ResponseUtils format(String json) {
		try {
			return MAPPER.readValue(json, ResponseUtils.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResponseUtils formatToList(String jsonData, Class clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

}
