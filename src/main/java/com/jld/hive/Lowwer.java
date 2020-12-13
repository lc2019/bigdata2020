package com.jld.hive;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

//将字符串转为小写
public class Lowwer extends UDF {
    public String evaluate(String value) {
        if (StringUtils.isNotEmpty(value)) {
            return value.toLowerCase();
        }
        return null;
    }
}
