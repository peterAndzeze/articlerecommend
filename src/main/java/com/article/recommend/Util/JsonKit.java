package com.article.recommend.Util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json操作
 */
public class JsonKit {
    /**
     * 将用户偏好词json 转为map
     * @param userPrefListJson
     * @return
     */
    public static CustomerHashMap<Integer,CustomerHashMap<String,Double>> jsonPrefListtoMap(String userPrefListJson){
        ObjectMapper objectMapper=new ObjectMapper();
        CustomerHashMap<Integer,CustomerHashMap<String,Double>> map=null;
        try
        {
            System.out.println("srcJson:"+userPrefListJson);
            map=objectMapper.readValue(userPrefListJson, new TypeReference<CustomerHashMap<Integer,CustomerHashMap<String,Double>>>(){});
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

}
