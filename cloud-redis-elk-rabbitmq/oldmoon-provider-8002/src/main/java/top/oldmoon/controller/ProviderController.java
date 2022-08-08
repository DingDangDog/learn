package top.oldmoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.oldmoon.entity.TestQuery;
import top.oldmoon.service.ProviderService;

@RestController
public class ProviderController {

    @Value("${server.port}")
    Integer port;
    @Autowired
    ProviderService providerService;

    /**
     * @description 测试springCloud 的 fegin调用（被调用）
     * @param id
     * @return
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
     * @return
     */
    @GetMapping("/save/para")
    public String savePara(@RequestParam String param){
        boolean redisVersion = providerService.savePara(param);
        return "{'code':"+redisVersion+"}";
    }

    /**
     * @description 测试redis查询
     * @param key
     * @return
     */
    @GetMapping("/get/para")
    public String getPara(@RequestParam String key){
        String redisVersion = providerService.getPara(key);
        return "{"+ key +":"+ redisVersion +"}";
    }

    /**
     * @description 测试Elasticsearch查询数据
     * @param test
     */
    @PostMapping("/test/searchElk")
    public String searchElasticS(@RequestBody TestQuery test) {

        String strjson = providerService.testElkUtils(test);

        return strjson;
    }

}
