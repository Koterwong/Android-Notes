package com.itheima27.xmlparserserializer.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

import com.itheima27.xmlparserserializer.entities.Person;

public class XmlUtils {

    private static final String TAG = "XmlUtils";

    /**
     * 把数据以xml形式写到本地
     */
    public void writeToLocal() throws Exception {

        List<Person> personList = new ArrayList<Person>();
        for (int i = 0; i < 30; i++) {
            personList.add(new Person(i, "王" + i, 20 + i));
        }

        XmlSerializer serializer = Xml.newSerializer();        // 创建Xmlserializer的对象
        // 指定序列化对象输出的流
        FileOutputStream fos = new FileOutputStream("/mnt/sdcard/person.xml");
        serializer.setOutput(fos, "utf-8");

        serializer.startDocument("utf-8", true);    // 写xml头

        serializer.startTag(null, "persons");    // 开始的根节点

        for (Person person : personList) {
            serializer.startTag(null, "person");
            serializer.attribute(null, "id", String.valueOf(person.getId()));

            // 写人的姓名
            serializer.startTag(null, "name");
            serializer.text(person.getName());
            serializer.endTag(null, "name");

            // 写人的年龄
            serializer.startTag(null, "age");
            serializer.text(String.valueOf(person.getAge()));
            serializer.endTag(null, "age");

            serializer.endTag(null, "person");
        }

        serializer.endTag(null, "persons");        // 结束的根节点

        serializer.endDocument();    // 标记xml输出完毕
    }

    /**
     * 从本地文件解析xml数据
     */
    public void parserXmlFromLocal() throws Exception {
        // 创建Pull解析器对象
        XmlPullParser parser = Xml.newPullParser();
        // 指定解析的文件
        FileInputStream fis = new FileInputStream("/mnt/sdcard/person.xml");
        parser.setInput(fis, "UTF-8");

        int eventType = parser.getEventType();        // 获得解析器的第一个事件类型

        List<Person> personList = null;
        Person person = null;
        String name;

        while (eventType != XmlPullParser.END_DOCUMENT) {        // 如果事件类型不等于结束的类型, 继续循环
            String tagName = parser.getName();        // 获得当前节点的名称

            // 解析数据
            switch (eventType) {
                case XmlPullParser.START_TAG:        // 代表开始节点 <persons>

                    if ("persons".equals(tagName)) {
                        // 初始化集合
                        personList = new ArrayList<Person>();
                    } else if ("person".equals(tagName)) {
                        person = new Person();
                        person.setId(Integer.valueOf(parser.getAttributeValue(null, "id")));
                    } else if ("name".equals(tagName)) {
                        name = parser.nextText();        // 当前是<name> 节点 nextText是取<name> 后边的值
                        person.setName(name);
                    } else if ("age".equals(tagName)) {
                        person.setAge(Integer.parseInt(parser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:        // 代表结束节点 </person>
                    if ("person".equals(tagName)) {        // 当前是</person>
                        // 把上面person的对象添加到集合中
                        personList.add(person);
                    }
                    break;
                default:
                    break;
            }

            eventType = parser.next();        // 赋值下一个事件类型
        }

        if (personList != null) {
            for (Person p : personList) {
                Log.i(TAG, p.toString());
            }
        }
    }
}
