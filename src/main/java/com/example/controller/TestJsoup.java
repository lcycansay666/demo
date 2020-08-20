/**
 * @Auther: PC-8
 * @Date: 2020/8/13 15:59
 * @Description:
 */
package com.example.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.example.entity.TestVo;
import com.example.util.excel.ExcelException;
import com.example.util.excel.ExcelUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: lcy
 * @Date: 2020/8/13 15:59
 * @Description:
 */


public class TestJsoup {


    /**
     * @param url 访问路径
     * @return
     */
    public static Document getDocument(String url) {
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static List<TestVo> returnAreaAndList(String url){
        Document doc = getDocument(url);

        // 获取目标HTML代码
        Elements elements1 = doc.select("td");
        //今天
        // Elements elements2 = elements1.select("[class=sky skyid lv2 on]");
        String area = elements1.get(0).text();
        System.out.println(area.split(":")[1]);

        Elements elements2 = elements1.select("[class=blue]");

        List<TestVo> list = new ArrayList<>();
        TestVo testVo = new TestVo();
        testVo.setName(area);
        list.add(testVo);
        int j = 0;
        for (int i = 0; i < elements2.size(); i++) {
            if (i < 16) {
                continue;
            }
            j++;
            TestVo testVo2 = new TestVo();
            testVo2.setName2(elements2.get(i).text());
            list.add(testVo2);
            if(elements2.get(i).text().indexOf("[详细]") > -1){
                TestVo testVo3 = new TestVo();
                testVo3.setName1(elements2.get(i-1).text());
                list.add(testVo3);
            }
            /*else{
                list.remove(j-1);
                if(j>2){
                    list.remove(j-2);
                }

            }*/
        }

        list = list.subList(0,list.size()-3);
        return list;
    }

    public static void main(String[] args) {

        TestJsoup t = new TestJsoup();
        Document doc = getDocument("http://www.tcmap.com.cn/zhejiangsheng/yuhang.html");
        // 获取目标HTML代码
        Elements elements1 = doc.select("td");
        //今天
        // Elements elements2 = elements1.select("[class=sky skyid lv2 on]");
        String area = elements1.get(0).text();
        System.out.println(area.split(":")[1]);

        Elements elements2 = elements1.select("[class=blue]");

        List<String> list = new ArrayList<>();

        for (int i = 0; i < elements2.size(); i++) {
            if (i >= 16) {
                list.add(elements2.get(i).text());
            }
        }
        System.out.println(list.toString());
    }


}
