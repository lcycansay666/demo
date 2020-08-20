/**
 * @Auther: PC-8
 * @Date: 2019/11/19 16:04
 * @Description:
 */
package com.example.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.Organization;
import com.example.entity.TestVo;
import com.example.util.excel.ExcelException;
import com.example.util.excel.ExcelUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lcy
 * @Date: 2019/11/19 16:04
 * @Description:
 */
@RestController
@RequestMapping("es")
public class TestController {

    @Autowired
    private TransportClient client;

    private String index = "zsa_index_security_test";
    private String type = "zsa_type_security_test";
    private int size = 10;
    private int from = 0;


    @GetMapping("/get")
    public ResponseEntity searchById(@RequestParam("id") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 通过索引、类型、id向es进行查询数据
        GetResponse response = client.prepareGet("zsa_index_security_test", "zsa_type_security_test", id).get();

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 返回查询到的数据
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity add(Organization organization) {
        try {
            // 将参数build成一个json对象
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();

            content.field("id", organization.getId());
            content.field("organizationName", organization.getOrganizationName());
            content.field("img", organization.getImg());
            content.endObject();

            IndexResponse response = client.prepareIndex("zsa_index_security_test", "zsa_type_security_test")
                    .setId(organization.getId())   //设置_id 不设置自动生成的id,长度为20个字符，URL安全，base64编码，GUID,分布式系统并行生成时不可能会发生冲突
                    .setSource(content)  //设置数据
                    .get();

            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(Organization organization) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (organization.getOrganizationName() != null) {
                builder.field("organizationName", organization.getOrganizationName());
            }
            if (organization.getImg() != null) {
                builder.field("img", organization.getImg());
            }

            builder.endObject();

            String id = String.valueOf(organization.getId());

            UpdateRequest updateRequest = new UpdateRequest("zsa_index_security_test", "zsa_type_security_test", id);
            updateRequest.doc(builder);
            UpdateResponse result = client.update(updateRequest).get();
            return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(String id) {
        DeleteResponse result = client.prepareDelete("zsa_index_security_test", "zsa_type_security_test", id).get();
        return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);
    }


    @RequestMapping(value = "getData")
    @ResponseBody
    public List<JSON> getData(Organization organization) {
        BoolQueryBuilder list = QueryBuilders.boolQuery();
        //增加排序
        SortBuilder sort1 = SortBuilders.fieldSort("id").order(SortOrder.ASC); //升序
        //SortBuilder sort1 = SortBuilders.fieldSort("id").order(SortOrder.DESC); //降序

        if (organization.getImg() != null) {
            list.must(QueryBuilders.termsQuery("img", organization.getImg())); //增加查询条件
        }

        /*if(organization.getId() != null){
            list.must(QueryBuilders.termsQuery("id",organization.getId()));
        }*/


        list.must(QueryBuilders.rangeQuery("id").from("1").to("2"));

        if (organization.getHasHealthImg() != null) {
            list.must(QueryBuilders.termsQuery("hasHealthImg", organization.getHasHealthImg()));
        }
        //matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
        //QueryBuilders.rangeQuery(字段).from(开始值).to(结束值)
        //termQuery：不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到。
        if (organization.getOrganizationName() != null) {
            TermQueryBuilder q1 = QueryBuilders.termQuery("organizationName", organization.getOrganizationName());

            list.must(QueryBuilders.matchQuery("organizationName", organization.getOrganizationName()));
        }

        SearchResponse response = client.prepareSearch(index).setQuery(list) // 设置查询器
                .setSize(size).setFrom(from).addSort(sort1) // 一次查询文档数
                .get();
        SearchHits listHits = response.getHits();
        List<JSON> ll = new ArrayList<>();
        for (SearchHit listHit : listHits) {

            JSONObject json = JSON.parseObject(listHit.getSourceAsString());
            ll.add(json);
        }

        return ll;
    }

    @ResponseBody
    @RequestMapping(value = "updateData")
    public void updateData(Organization organization) {

        Map<String, String> map = new HashMap<>();
        map.put("id", "147852");
        map.put("organizationName", "这是测试es使用2");
        map.put("img", "es.img");
        map.put("hasHealthImg", "0");

        client.prepareUpdate(index, type, organization.getId()).setDoc(map)
                .execute().actionGet();

    }

    /**
     * 索引    类型名   _id  (可以不传,自动生成20位的_id)
     * prepareIndex(index, type,organization.getId())
     *
     * @param organization
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addData")
    public IndexResponse addData(Organization organization) {

        Map<String, String> map = new HashMap<>();
        map.put("id", organization.getId());
        map.put("organizationName", "这是测试es使用");
        map.put("img", "es.img");
        map.put("hasHealthImg", "0");
        IndexResponse aa = client.prepareIndex(index, type, organization.getId()).setSource(map)
                .execute().actionGet();

        return aa;

    }


    @ResponseBody
    @GetMapping(value = "hello")
    public String sayHello() {
        System.out.println("1111111");
        return "hello";
    }


    //"http://www.tcmap.com.cn/zhejiangsheng/yuhang.html"
    @ResponseBody
    @GetMapping(value = "testPc")
    public void testPc(HttpServletResponse response,String url) {

        try {
            List<TestVo> list = TestJsoup.returnAreaAndList(url);
            ExcelUtil.writeExcel(response, list, list.get(0).getName(), "000", ExcelTypeEnum.XLS, TestVo.class);
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }


}
