package top.oldmoon.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.oldmoon.model.Test;
import top.oldmoon.model.TestQuery;
import top.oldmoon.service.ProviderService;

import java.util.List;

@RestController
public class ProviderController {

    @Value("${server.port}")
    Integer port;
    @Autowired
    ProviderService providerService;

    /**
     * @description 测试springCloud 的 fegin调用（被调用）
     * @param id
     */
    @GetMapping("/test/getInfo/{id}")
    public String getInfo(@PathVariable("id") Integer id){
        return "{'id':"+id+",'server':"+ port +"}";
    }

    @GetMapping("/getRedisV/{redis}")
    public String getRedisVersion(@PathVariable("redis") String param){
        String redisVersion = providerService.getRedisVersion();
        return "{'server':"+8001+", 'redisVersion'"+redisVersion+"}";
    }

    /**
     * @description 测试redis存储
     * @param param
     */
    @GetMapping("/save/para")
    public String savePara(@RequestParam String param){
        boolean redisVersion = providerService.savePara(param);
        return "{'code':"+redisVersion+"}";
    }

    /**
     * @description 测试redis查询
     * @param key
     */
    @GetMapping("/get/para")
    public String getPara(@RequestParam String key){
        String redisVersion = providerService.getPara(key);
        return "{"+ key +":"+ redisVersion +"}";
    }

    /**
     * @description 测试Elasticsearch保存数据
     * @param test
     */
    @PostMapping("/test/saveElk")
    public String saveElasticS(@RequestBody Test test) {
        boolean result = false;
        result = providerService.saveElasticS(test);
        return "{result:"+result+"}";
    }

    /**
     * @description 测试Elasticsearch查询数据
     * @param test
     */
    @PostMapping("/test/searchElk")
    public String searchElasticS(@RequestBody TestQuery test) {

        List<Test> testList = providerService.searchElasticS(test);

        String result = JSON.toJSONString(testList);

        return result;
    }
}
