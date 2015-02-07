package com.sanguo.payment.alipay.config;

/**
 * Created by Administrator on 2015/1/14.
 */
public class Config {

   public static String partner = "2088511699070262";
   //public static String partner = "2088811472492730";
    // 商户的私钥
   public static String key = "zjgbcg8ql5a3g37pxjjcbl9nlcc6f2t2";
    //public static String key = "6dbt2cve1pgwrdxkcxdoub2cmx25wsuv";

    public static String seller_email = "zhenjing@wuxiseo.cn";
    //public static String seller_email = "wx1926556079@163.com";
    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "D:\\";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 签名方式 不需修改
    public static String sign_type = "MD5";
}
