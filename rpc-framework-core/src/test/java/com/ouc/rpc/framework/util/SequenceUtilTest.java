package com.ouc.rpc.framework.util;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class SequenceUtilTest {


    @Test
    void testSequenceId() {
        Long sequenceId = SequenceUtil.getSequenceId();
        System.out.println(sequenceId);
    }


    @Test
    void testOutputStream() throws UnsupportedEncodingException {

        byte[] data;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write("Hello ".getBytes("UTF-8"));
            output.write("world!".getBytes("UTF-8"));
            data = output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new String(data, "UTF-8"));
    }
}
