package top.oldmoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.oldmoon.fergn.ProviderService;

/**
 * @description 普通controller
 * @RestController 等同于@Controller和@ResponseBody整合，可以理解为返回的都是数据串，一般为json
 */
@RestController
public class ConsumerController {

    @Autowired
    ProviderService service;

    /**
     * @GetMapping   声明接口是get请求方式
     * @RequestParam 从uri的'?'后获取参数
     * @PathVariable 直接从uri中获取参数
     * @RequestBody  从请求体中获取参数，且一般为实体类型
     * @param id
     */
    @GetMapping("/test/consumer/{id}")
    public String getProviderInfo(@PathVariable("id") Integer id){

        return service.getInfo(id);
    }
}
