package com.dzxy.aiutil;

import java.net.URLEncoder;

public class FaceAdd {
    public static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            // 本地文件路径
            String filePath = "";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");


            String param = "user_id=" + "userid9" + "&user_info=" + "" + "&group_id=" + "halogen_1" + "&image_type=BASE64" + "&image=" + imgParam;
            AuthService auth = new AuthService();

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = auth.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("addface:" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
