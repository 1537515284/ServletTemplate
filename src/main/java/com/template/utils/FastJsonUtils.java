package com.template.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class FastJsonUtils {
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteDateUseDateFormat,//JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

        /**
         * 功能描述：把java对象转换成JSON数据
         * @param object java对象
         * @return JSON数据
         */
//         public static String toJSONString(Object object) {
//             return JSON.toJSONString(object, config, features);
//         }

        public static String toJSONString(Object object) {
            return JSON.toJSONString(object, features);
        }

        /**
         * 功能描述：把java对象转换成JSON数据（不自定义规则）
         * @param object java对象
         * @return JSON数据
         */
        public static String toJSONStringNoFeatures(Object object) {
            return JSON.toJSONString(object, config);
        }


    /**
     * 功能描述：把JSON数据转换成指定的java对象
     * @param jsonData JSON数据
     * @return Object
     */
    public static Object toBean(String jsonData) {
        return JSON.parse(jsonData);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return 指定的java对象
     */
    public static <T> T toBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换为数组
     * @param jsonData JSON数据
     * @return Object[]
     */
    public static <T> Object[] toArray(String jsonData) {
        return toArray(jsonData, null);
    }

    /**
     * 功能描述：把JSON数据转换为数组
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return Object[]
     */
    public static <T> Object[] toArray(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz).toArray();
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return List<T>
     */
    public static <T> List<T> toList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * Map转对象
     * @param map
     * @param clazz
     * @return Object
     */
    public static <T> T mapToObject(Map map, Class<T> clazz) {
        String jsonData = FastJsonUtils.toJSONString(map);
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 对象转Map
     * @param object
     * @return Map
     */
    public static Map objectToMap(Object object) {
        String jsonData = FastJsonUtils.toJSONString(object);
        return FastJsonUtils.toBean(jsonData,Map.class);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
     * @param jsonData JSON数据
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getJsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    /**
     * 功能描述：通过Response输出JSON字符串
     * @param res
     * @param response
     * @throws IOException
     */
    public static void writeJson(Result res, HttpServletResponse response) throws IOException {
        String s = FastJsonUtils.toJSONString(res);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(s);
    }
}
