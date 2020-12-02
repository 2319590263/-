package com.dl;

import com.dl.entity.XmlPojo;
import com.dl.utiles.XmlAnalysis;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        XmlPojo xmlPojo = XmlAnalysis.analysisFile("/home/yeren/ActiveMq/activemq/conf/activemq.xml");
//        XmlPojo xmlPojo = XmlAnalysis.analysisString("<sites>\n" +
//                "    <site>\n" +
//                "        <name>菜鸟教程</name>\n" +
//                "        <url>www.runoob.com</url>\n" +
//                "    </site>\n" +
//                "    <site>\n" +
//                "        <name>Google</name>\n" +
//                "        <url>www.google.com</url>\n" +
//                "    </site>\n" +
//                "    <site>\n" +
//                "        <name>淘宝</name>\n" +
//                "        <url>www.taobao.com</url>\n" +
//                "    </site>\n" +
//                "</sites>");
        String xml = XmlAnalysis.XmlPojoToXMl(xmlPojo);
        System.out.println(xml);
    }

}
