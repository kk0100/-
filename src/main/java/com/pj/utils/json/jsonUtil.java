package com.pj.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

import static com.pj.project4sp.SP.objectMapper;

public class jsonUtil {

    /**
     * map转json
     *
     * @param map
     * @return
     */
    public static String mapToString(Map<String, Object> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    public static Object jsonAddKey(String jsonString) throws JsonProcessingException {
        //实现json内部加新的键值对。
        //发送消息"{\"id:"+spMessage.getId()+","+"\""+"content:"+messageContent+"\"}
        // 将JSON字符串转换为JsonNode
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
        ObjectNode objectNode = (ObjectNode) jsonNode;
        return objectNode;
    }

    public static String getWarnType(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 将JSON字符串转换为JsonNode
            JsonNode rootNode = mapper.readTree(jsonString);

            // 访问'content'对象
            JsonNode contentNode = rootNode.get("content");

            // 获取'warn_type'的值
            JsonNode warnTypeNode = contentNode.get("warn_type");
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return jsonString;
    }
}
