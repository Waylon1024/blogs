package top.naccl.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author WayLon
 * @create 2023-05-21 21:54
 * 备注:
 */
public class StringEmptyToNullDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser json, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = json.getValueAsString();
        value = value.trim();
        return StrUtil.equals("", value) ? null : value;
    }
}
