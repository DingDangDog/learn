package top.oldmoon.service;

import top.oldmoon.model.TestQuery;
import top.oldmoon.model.Test;

import java.util.List;

public interface ProviderService {

    public String getRedisVersion();

    public boolean savePara(String para);

    public String getPara(String key);

    public boolean saveElasticS(Test test);

    public List<Test> searchElasticS(TestQuery test);
}
