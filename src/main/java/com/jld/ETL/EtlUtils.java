package com.jld.ETL;

/**
 * 实现清洗的方法
 */
public class EtlUtils {
    public static String etl(String s) {
        String[] words = s.split("\t");
        //长度要求
        if (words.length < 10) {
            return null;
        }
        //去掉类别的空格
        words[3].replaceAll(" ", "");

        //将相关视频的分隔符替换为&
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < words.length; i++) {
            if (i < 9) {
                sb.append(words[i] + "\t");
            } else {
                sb.append(words[i] + "&");
            }
        }
        //去除&
        String s1 = sb.toString();
        return s1.substring(0, s1.length() - 1);
    }
}