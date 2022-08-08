package top.oldmoon.service;

import top.oldmoon.entity.TestQuery;

public interface ProviderService {

    public String getRedisVersion();

    public boolean savePara(String para);

    public String getPara(String key);

    public String testElkUtils(TestQuery query);
}
