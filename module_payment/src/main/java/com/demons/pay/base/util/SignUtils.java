package com.demons.pay.base.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 签名工具类
 *
 * @author demons
 */
public class SignUtils {

    public static TreeMap<String, Object> toMap(String str) {
        Gson gson = new Gson();
        TreeMap<String, Object> signMap = gson.fromJson(str, new TypeToken<TreeMap<String, Object>>() {
        }.getType());
        return signMap;
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> map = toMap(message);
        for (TreeMap.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || "".equals(value)) {
                continue;
            }
            sb.append(key).append("=").append(value).append("&");
        }
        sb.append("key=").append(secret);
        String content = sb.toString();
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(content.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return hash;
    }
}

