package top.oldmoon.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.oldmoon.entity.TestQuery;
import top.oldmoon.esUtils.utils.ElasticSUtils;
import top.oldmoon.service.ProviderService;
import top.oldmoon.utils.RedisUtils;

import java.io.IOException;

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

    public String testElkUtils(TestQuery query) {
        String search = null;
        try {
            search = elasticSUtils.search(query);
        } catch (IOException e) {
            logger.error("elastic查询失败");
            e.printStackTrace();
        }

        return search;
    }
}
