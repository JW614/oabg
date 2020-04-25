package com.dzxy.aiutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceMatch {
    public String match(String image2) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            byte[] bytes1 = FileUtil.readFileByBytes("/Users/jiangwen/Desktop/3.jpg");
//            byte[] bytes2 = FileUtil.readFileByBytes("/Users/jiangwen/Desktop/2.jpg");
            String image1 = Base64Util.encode(bytes1);
//            String image2 = Base64Util.encode(bytes2);
            List<Map<String, Object>> images = new ArrayList<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);

            String param = GsonUtils.toJson(images);
            AuthService auth = new AuthService();
            String accessToken = auth.getAuth();
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            String score = result.split(",")[5].split(":")[2];
            return score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        FaceMatch faceMatch = new FaceMatch();
//        System.out.println(faceMatch.match());
//    }
}
