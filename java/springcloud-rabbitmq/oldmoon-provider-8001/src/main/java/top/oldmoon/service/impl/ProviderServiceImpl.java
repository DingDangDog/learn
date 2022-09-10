package top.oldmoon.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.oldmoon.model.TestQuery;
import top.oldmoon.utils.RedisUtils;
import top.oldmoon.utils.elasticsearch.ElasticSUtils;
import top.oldmoon.model.Test;
import top.oldmoon.service.ProviderService;

import java.util.Date;
import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    private static final Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ElasticSUtils elasticSUtils;

    /**
     * @return
     */
    public String getRedisVersion() {
        String redis_version = (String) redisUtils.get("redis_version");
        return redis_version;
    }

    public boolean savePara(String para) {
        boolean redis = redisUtils.set("redis", para);
        return redis;
    }

    public String getPara(String key) {
        String para = (String) redisUtils.get(key);
        return para;
    }

    public boolean saveElasticS(Test test) {
        boolean result = false;
        try {
            test.setNowDateTime(new Date());
            result = elasticSUtils.insert("_doc", "test", JSON.toJSONString(test));
        } catch (Exception e) {
            logger.error("ElasticSearch保存数据失败：数据报文：" + JSON.toJSONString(test));
            logger.error("ElasticSearch保存数据失败，异常信息：" + e.getMessage());
            return false;
        }
        return result;
    }

    public List<Test> searchElasticS(TestQuery test) {
        String jsonStr;
        try {
            jsonStr = elasticSUtils.search(test);
        } catch (Exception e) {
            logger.error("ElasticSearch查询数据失败，异常信息：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        List<Test> testList = (List<Test>) JSON.parse(jsonStr);

        return testList;
    }
}
