package com.ouc.rpc.framework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
@Setter
@Getter
public class ServerConfig implements InitializingBean {

    @Value("")
    private String id;

    private Integer port;

    /**
     * @Description: 依赖注入完成之后自动回调该函数
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("server config");
    }
}
