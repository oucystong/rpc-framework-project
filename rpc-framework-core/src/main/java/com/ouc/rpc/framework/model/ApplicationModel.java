package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description: 描述应用信息模型
 * @Author: Mr.Tong
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationModel implements ApplicationContextAware {


    /**
     * @Description: 应用ID
     */
    private String applicationId;

    /**
     * @Description: 应用名称
     */
    private String applicationName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.setApplicationContext(applicationContext);
    }
}
