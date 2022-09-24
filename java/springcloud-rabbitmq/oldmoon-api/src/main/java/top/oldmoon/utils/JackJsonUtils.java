package top.oldmoon.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json 工具类
 *
 * @author jason
 * 2020/2/15 8:48 下午
 */
@Slf4j
public final class JackJsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JackJsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 对象转为json
     *
     * @param object
     * @return
     */
    @SneakyThrows
    public static String toJSONString(Object object) {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * json转为对象
     *
     * @param json
     * @return
     */
    @SneakyThrows
    public static Object parseObject(String json) {
        return OBJECT_MAPPER.readValue(json, Object.class);
    }

    /**
     * json转为对象
     *
     * @param json
     * @return
     */
    @SneakyThrows
    public static <T> T parseObject(String json, Class<T> clazz) {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    /**
     * 根据TypeReference格式化数据
     *
     * @author hupg
     * @since 2022/8/19 9:46
     */
    public static <T> T parseType(String content, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(content, valueTypeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换list
     *
     * @param json  json
     * @param clazz 实体类类型
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        final JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return OBJECT_MAPPER.readValue(json, javaType);
    }

    public static void main(String[] args) {
        String json = "{ \"database\": \"husoul\", \"tableName\": \"user\", \"tableComment\": \"\", \"columns\": [ { \"columnName\": \"id\", \"dataType\": \"varchar\", \"columnType\": \"varchar(255)\", \"isNullable\": \"NO\", \"columnKey\": \"PRI\", \"columnComment\": \"主键\" }, { \"columnName\": \"name\", \"dataType\": \"varchar\", \"columnType\": \"varchar(255)\", \"isNullable\": \"YES\", \"columnKey\": \"\", \"columnComment\": \"\" }, { \"columnName\": \"username\", \"dataType\": \"varchar\", \"columnType\": \"varchar(255)\", \"isNullable\": \"YES\", \"columnKey\": \"\", \"columnComment\": \"\" }, { \"columnName\": \"password\", \"dataType\": \"varchar\", \"columnType\": \"varchar(255)\", \"isNullable\": \"YES\", \"columnKey\": \"\", \"columnComment\": \"\" } ] }";

        Map parse = JackJsonUtils.parseObject(json, Map.class);
        System.out.println(parse);
    }
}
