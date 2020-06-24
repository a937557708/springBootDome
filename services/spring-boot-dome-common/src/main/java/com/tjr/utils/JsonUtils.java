package com.tjr.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @ClassName: JsonUtils
 * @author hanwl
 * @date 2019年01月22日
 * @Description: TODO
 */
public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * Json格式的字符串向JavaBean转换，传入空串将返回null
	 * 
	 * @param strBody
	 *            Json格式的字符串
	 * @param c
	 *            目标JavaBean类型
	 * @return JavaBean对象
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T json2Object(String strBody, Class<T> c)
			throws JsonParseException, JsonMappingException, IOException {
		if (strBody == null || "".equals(strBody)) {
			return null;
		} else {
			return OBJECT_MAPPER.readValue(strBody, c);
		}
	}

	/**
	 * Json格式的字符串向JavaBean转换，传入空串将返回null
	 * 
	 * @param strBody
	 *            Json格式的字符串
	 * @param c
	 *            目标JavaBean类型
	 * @return JavaBean对象, 如果解析失败返回 null
	 */
	public static <T> T decodeJson(String strBody, Class<T> c) {
		if (strBody == null || "".equals(strBody)) {
			return null;
		} else {
			try {
				return OBJECT_MAPPER.readValue(strBody, c);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 *
	 * @param strBody
	 * @param c
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object json2ComplexObject(String strBody)
			throws JsonParseException, JsonMappingException, IOException {
		if (strBody == null || "".equals(strBody)) {
			return null;
		} else {
			// 每个属性的实际类型是string
			return OBJECT_MAPPER.readValue(strBody, Object.class);
		}
	}

	/**
	 * Json格式的字符串向JavaBean List集合转换，传入空串将返回null
	 * 
	 * @param strBody
	 * @param c
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> json2ObjectList(String strBody, Class<T> c)
			throws JsonParseException, JsonMappingException, IOException {
		if (strBody == null || "".equals(strBody)) {
			return null;
		} else {
			JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, c);
			return (List<T>) OBJECT_MAPPER.readValue(strBody, javaType);
		}
	}

	/**
	 * Json格式的字符串向JavaBean List集合转换，传入空串将返回null
	 * 
	 * @param strBody
	 * @param c
	 * @return 对象列表，解析失败返回 null
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> decodeJsonToList(String strBody, Class<T> c) {
		if (strBody == null || "".equals(strBody)) {
			return null;
		} else {
			JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, c);
			try {
				return (List<T>) OBJECT_MAPPER.readValue(strBody, javaType);
			} catch (IOException e) {
				e.printStackTrace();

				return null;
			}
		}
	}

	/**
	 * Json格式的字符串向List<String>集合转换，传入空串将返回null
	 * 
	 * @param strBody
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<String> json2List(String strBody) throws JsonParseException, JsonMappingException, IOException {
		return json2ObjectList(strBody, String.class);
	}

	/**
	 * Object转为Json格式字符串的方法
	 * 
	 * @param o
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String object2Json(Object o) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(o);
	}

	/**
	 * Object转为Json格式字符串的方法
	 * 
	 * @param o
	 * @return 对象的json字符串，如果处理过程中出错，返回null
	 */
	public static String encodeObject(Object o) {
		try {
			return OBJECT_MAPPER.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void parserXml(String strXml) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new InputSource(new StringReader(strXml)));
			NodeList sessions = document.getChildNodes();
			/*HashMap<String,String> hash=new HashMap<String,String>();*/
			String json="";
			for (int i = 0; i < sessions.getLength(); i++) {
				Node session = sessions.item(i);
				NodeList sessionInfo = session.getChildNodes();
				for (int j = 0; j < sessionInfo.getLength(); j++) {
					Node node = sessionInfo.item(j);
					NodeList sessionMeta = node.getChildNodes();
					for (int k = 0; k < sessionMeta.getLength(); k++) {
						System.out.println(sessionMeta.item(k).getTextContent());
						json=sessionMeta.item(k).getTextContent();
						// hash.put(sessionMeta.item(k).getNodeName(), sessionMeta.item(k).getTextContent());
					}
				}
			}
			System.out.println("获取json"+json);
			try {
				JSONObject result=JSONObject.parseObject(json);
				//result.get("message");
				//result.get("statusCode");
				if(result.get("statusCode").equals("S")) {
					System.out.println(result.get("message"));
				}else {
					System.out.println("失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("解析完毕");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}


	/**
	 * xml转换json
	 * @param xml
	 * @return
	 * @throws JSONException
	 */
	public static org.json.JSONObject XML2Json(String xml) throws JSONException{
		org.json.JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(xml);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return xmlJSONObj;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String strXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:method xmlns:ns2=\"http://service.inquiry.els.com/\"><return>{\"message\":\"数据成功\",\"statusCode\":\"S\"}</return></ns2:method></soap:Body></soap:Envelope>";
		XML2Json(strXml);
	}
}