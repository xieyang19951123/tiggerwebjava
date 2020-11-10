package io.renren.modules.tigger.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TransOrder {

    /**
     * 获取随机订单号
     */

    public static String getOrder(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String format = simpleDateFormat.format(new Date());
        return format+UUID.randomUUID().toString().replaceAll("-","").substring(0,10);

    }

    public static void main(String[] args) {
        System.out.println(getOrder());
    }


    public static String getRecommend(){
        return RandomStringUtils.randomAlphanumeric(6);//获取随机的推荐码
    }
}
