package com.dzxy.aiutil;

import java.util.HashMap;
import java.util.Map;

/**
 * 人脸检测与属性分析
 */
public class FaceDetect {

    public static String detect() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            byte[] bytes = FileUtil.readFileByBytes("/Users/jiangwen/Desktop/红底.jpg");
            String img = Base64Util.encode(bytes);
            map.put("image", img);
            map.put("face_field", "faceshape,facetype");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.2eeda9409a6fef84f5cb5d6d6f145002.2592000.1579400081.282335-18065847";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        detect();
    }
}


