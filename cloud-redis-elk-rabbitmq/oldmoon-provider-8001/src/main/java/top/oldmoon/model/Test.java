package top.oldmoon.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.oldmoon.annotation.ElasticSIndex;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ElasticSIndex(index = "test", type="_doc")
public class Test implements Serializable {
    private String server;
    private String name;
    private String date;
    private String time;
    private Date nowDateTime;
}
