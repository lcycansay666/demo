/**
 * @Auther: PC-8
 * @Date: 2020/11/19 16:10
 * @Description:
 */
package com.example.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lcy
 * @Date: 2020/11/19 16:10
 * @Description:
 */
public class Test1 {


    private static final String ARTEMIS_PATH = "/artemis";
    private static final String host = "218.205.127.176:9999";
    private static final String appKey = "25452831";
    private static final String appSecret = "QeVUQX6OJXY3X8ytMZUs";

    private static final Map<String, String> path = new HashMap<>();

    static {
        // artemis网关服务器ip端口
        ArtemisConfig.host = host;
        // 秘钥 appKey
        ArtemisConfig.appKey = appKey;
        // 秘钥 appSecret
        ArtemisConfig.appSecret = appSecret;
    }

    public static void main(String[] args) {
        //测试流地址
        getHlsUrl("331100000081462190");
        //getCameraInfoPage();
    }


    /**
     * 获取监控点预览取流URLv2
     *
     * @param cameraIndexCode 接口请求路径
     * @return 返回监控点列表
     */
    public static String getHlsUrl(String cameraIndexCode) {

        String hlsUrl ="";
        String url = ARTEMIS_PATH + "/api/mss/v1/hls/" + cameraIndexCode;

        Map<String, String> querys = new HashMap<String, String>();//get请求的查询参数
        // querys.put("indexCode", cameraIndexCode);
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", url);
            }
        };
        String result = ArtemisHttpUtil.doGetArtemis(path, querys, null, null);
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.get("code") + "";

        if ("0".equals(code)) {
            String data = jsonObject.get("data") + "";
            JSONObject jsonObject1 = JSON.parseObject(data);
            hlsUrl = jsonObject1.get("url") + "";

        }
        return hlsUrl;
    }



    public static String findControlUnitPage() {


        String url = ARTEMIS_PATH + "/api/common/v1/remoteCameraInfoRestService/findControlUnitPage";

        Map<String, String> querys = new HashMap<String, String>();//get请求的查询参数
        // querys.put("indexCode", cameraIndexCode);
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", url);
            }
        };
        String result = ArtemisHttpUtil.doGetArtemis(path, querys, null, null);
        System.out.println(result);
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.get("code") + "";

     /*   if ("0".equals(code)) {
            String data = jsonObject.get("data") + "";
            JSONObject jsonObject1 = JSON.parseObject(data);
            hlsUrl = jsonObject1.get("url") + "";
            logger.info(hlsUrl);

        }*/
        return "";
    }

}
