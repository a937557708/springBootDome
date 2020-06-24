package com.tjr.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;





/**
 * Jaxb工具类 xml和java类相互转换
 *
 * @author sunjianbo
 * @date 2017年4月17日
 */
public class JaxbXmlUtil {

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * pojo转换成xml 默认编码UTF-8
     *
     * @param obj 待转化的对象
     * @return xml格式字符串
     * @throws Exception JAXBException
     */
    public static String convertToXml(Object obj) throws Exception {
        return convertToXml(obj, DEFAULT_ENCODING);
    }

    /**
     * pojo转换成xml
     *
     * @param obj 待转化的对象
     * @param encoding 编码
     * @return xml格式字符串
     * @throws Exception JAXBException
     */
    public static String convertToXml(Object obj, String encoding) throws Exception {
        String result = null;

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 指定是否使用换行和缩排对已编组 XML 数据进行格式化的属性名称。
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        result = writer.toString();

        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml xml格式字符串
     * @param t 待转化的对象
     * @return 转化后的对象
     * @throws Exception JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJavaBean(String xml, Class<T> t) throws Exception {
        T obj = null;
        JAXBContext context = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        obj = (T) unmarshaller.unmarshal(new StringReader(xml));
        return obj;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String strXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:eapp=\"http://wx.gmw9.com\">\n" +
                "   <soapenv:Header>\n" +
                "      \n" +
                "   </soapenv:Header>\n" +
                "   <soapenv:Body>\n" +
                "      <eapp:aaaaaa>\n" +
                "         <eapp:aa1>041</eapp:aa1>\n" +
                "         <eapp:aa2>5000</eapp:aa2>\n" +
                "         <eapp:aa3>?</eapp:aa3>\n" +
                "         \n" +
                "      </eapp:aaaaaa>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        convertToJavaBean(strXml, RequestEntity.class);
    }
}