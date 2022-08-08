package top.oldmoon.fergn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 声明Feign调用，并配置被调用方的调用地址，两种方式：
 *      name/value = "服务名称（配置文件中配置的 application.name）" 可以通过zuul实现路由，并实现负载均衡等高级应用，微服务集群等大型场景必用
 *          name/value = "provider-test",
 *      url = "服务地址" 不路由，没有负载均衡，可用于简单接口的系统
 *          url = "localhost:8001"
 */
@FeignClient(value = "provider-test")
public interface ProviderService {

    /**
     * @deacription 调用服务提供者的controller方法，一般声明为被调用方法相同的抽象方法
     * @param id
     */
    @GetMapping("/test/getInfo/{id}")
    public String getInfo(@PathVariable("id") Integer id);
}
