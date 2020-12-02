package com.dl.utiles;

import com.dl.entity.XmlPojo;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

public class XmlAnalysis {

    public static String XmlPojoToXMl(XmlPojo xmlPojo){
        StringBuffer xml = new StringBuffer();
        String tagName = xmlPojo.getTagName();

        xml.append("<"+tagName+" ");//添加标签头部

        //添加标签属性
        Map<String,String> labelProperties = xmlPojo.getLabelProperties();
        Iterator keys = labelProperties.keySet().iterator();
        while (keys.hasNext()){
            String key = String.valueOf(keys.next());
            String value = String.valueOf(labelProperties.get(key));
            xml.append(key+"="+value+" ");
        }

        //补上标签结尾
        xml.append(">");

        //添加标签value
        xml.append(xmlPojo.getValue());

        //循环遍历标签
        Iterator<XmlPojo> iterator = xmlPojo.getSubTags().iterator();
        while (iterator.hasNext()){
            xml.append(XmlPojoToXMl(iterator.next()));
        }

        //添加结束标签
        xml.append("</"+tagName+">");

        return xml.toString();
    }

    public static XmlPojo analysisString(String str) throws FileNotFoundException, DocumentException {
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        return analysisStream(inputStream);
    }

    public static XmlPojo analysisFile(String file) throws FileNotFoundException, DocumentException {
        InputStream inputStream = new FileInputStream(file);
        return analysisStream(inputStream);
    }

    public static XmlPojo analysisFile(File file) throws FileNotFoundException, DocumentException {
        InputStream inputStream = new FileInputStream(file);
        return analysisStream(inputStream);
    }

    public static XmlPojo analysisStream(InputStream inputStream) throws DocumentException {
        //1.创建Reader对象
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = reader.read(inputStream);
        //3.获取根节点
        Element rootElement = document.getRootElement();
        XmlAnalysis xmlAnalysis = new XmlAnalysis();
        List<XmlPojo> xmlPojos = xmlAnalysis.xmlAnalysis(rootElement);
        XmlPojo xmlPojo = xmlAnalysis.assembleXmlPojov2(xmlPojos);
        return xmlPojo;
    }

    private XmlAnalysis() {

    }

    private Integer level = 0;//用了表示当前xmlPojo的标签等级
    private LinkedList<XmlPojo> xmlPojos = new LinkedList<>();//使用链表结构的list集合已便在list的头部插入XmlPojo

    //将Element及它的子Element全部解析成XmlPojo对象并添加到xmlPojos中
    public List<XmlPojo> xmlAnalysis(Element element) {
        //获取element下的所有子element
        Iterator<Element> elementIterator = element.elementIterator();
        //将element的对应属性set进对象中
        XmlPojo xmlPojo = new XmlPojo();
        xmlPojo.setLevel(level++);//设置标签等级
        xmlPojo.setTagName(element.getName());
        xmlPojo.setValue(element.getTextTrim());
        HashMap<String, String> map = new HashMap<>();
        List<Attribute> attributes = element.attributes();
        for (Attribute attribute : attributes) {
            map.put(attribute.getName(), attribute.getValue());
        }
        xmlPojo.setLabelProperties(map);
        //将注入好的对象添加进xmlPojos中的头部
        xmlPojos.addFirst(xmlPojo);
        //循环遍历所有子element，并执行以上操作
        while (elementIterator.hasNext()) {
            Element next = elementIterator.next();
            xmlAnalysis(next);
            level--;//子element遍历完毕将level回退到上一级的状态
        }
        return xmlPojos;
    }

    //将xmlPojos中的每个xmlPojo的level至进行比较，当前者的level比后者的level小1则认定前者是后者的子标签，将前者添加进后者中的subTag
    //和比较算法中的冒泡算法非常像
    public XmlPojo assembleXmlPojov2(List<XmlPojo> xmlPojos) {
        //经典冒泡算法
        for (int i = 0; i < xmlPojos.size(); i++) {
            XmlPojo xmlPojo1 = xmlPojos.get(i);
            Integer level1 = xmlPojo1.getLevel();
            for (int j = i + 1; j < xmlPojos.size(); j++) {
                XmlPojo xmlPojo2 = xmlPojos.get(j);
                Integer level2 = xmlPojo2.getLevel();
                //从这里为止之上都和冒泡算法一致
                //当level1比level2小1时则将xmlPojo1添加进xmlPojo2中，并退出当前循环
                if (level1 - 1 == level2) {
                    xmlPojo2.getSubTags().add(xmlPojo1);
                    break;
                }
            }
        }
        //返回根XmlPojo
        return xmlPojos.get(xmlPojos.size() - 1);
    }

}
