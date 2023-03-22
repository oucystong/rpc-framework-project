package com.ouc.rpc.framework;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class JmhTest {
    @Benchmark
    public void wellHelloThere() {
        // this method was intentionally left blank.
    }


    @Test
    void testJmh() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
