package com.tang.mall.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {

    private static final String key = "957668bba54432d125dd580806790484";


    @Test
    public void Test01() {
        List<String> list = JSONArray.parseArray("['1_商品管理员', '5_超级管理员']", String.class);
        System.out.println(list.size());



    }

    public static void main(String[] args) throws Exception {
//        getKey();
        Map<String, String> map = new HashMap<>();
        map.put("id", "100");
        map.put("createTime", System.currentTimeMillis() + "");
        map.put("expireTime", (System.currentTimeMillis() + 60) + "");
        String token = createToken(map);
        System.out.println(token);
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmVUaW1lIjoiMzczMzc5NDQ2NDA5MjI0MTkyMCIsImlkIjoiMTAwIiwiY3JlYXRlVGltZSI6IjE1OTk0NTg1ODA1NDQifQ.WbWqytpWarlpKXnv3UzeRALXDAozlVfbLIogl1BQNqY";
        Map<String, Object> stringObjectMap = parseToken(token);
        stringObjectMap.forEach((key, value) -> {
            System.out.println("key--->" + key + ",value--->" + value);
        });
    }

    /**
     * 解析token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Object> parseToken(String token){
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(key);
            // 1.秘钥验证通过
            if (jwsObject.verify(jwsVerifier)) {
                Payload payload = jwsObject.getPayload();
                Map<String, Object> resultMap = JSON.parseObject(payload.toString(), Map.class);
                long expireTime = Long.parseLong(resultMap.get("expireTime").toString());
                // 2.验证时间是否过期
                if (System.currentTimeMillis() >= expireTime) {
                    throw new RuntimeException("token 过期了...");
                }
                return resultMap;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        throw new RuntimeException("token 解析异常!");
    }

    /**
     * 创建token，采用HS256算法
     * @param map
     * @return
     * @throws Exception
     */
    public static String createToken(Map<String, String> map) throws Exception {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(new JSONObject(map));
//        System.out.println("jwsHeader--->" + jwsHeader);
//        System.out.println("payload--->" + payload);
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(key);
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     * 获取签名的算法
     * @throws Exception
     */
    private static void getKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        //要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        String s = byteToHexString(b);
        System.out.println(s);
    }

    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }
}
