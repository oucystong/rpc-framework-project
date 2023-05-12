package com.ouc.rpc.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 产生Long类型的UUID
 * @Author: Mr.Tong
 */
public class SequenceUtil {


    private static AtomicLong id;


    /**
     * @Description: 产生唯一ID
     */
    public synchronized static Long getSequenceId() {
        //如果需要更长 或者更大冗余空间, 只需要 time * 10^n   即可
        //当前可保证1毫秒 生成 10000条不重复
        Long time = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())) * 10000;
        if (id == null) {
            id = new AtomicLong(time);
            return id.get();
        }
        if (time <= id.get()) {
            id.addAndGet(1);
        } else {
            id = new AtomicLong(time);
        }
        return id.get();
    }

}
