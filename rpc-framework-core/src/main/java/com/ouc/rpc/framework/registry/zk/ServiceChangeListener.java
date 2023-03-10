package com.ouc.rpc.framework.registry.zk;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;


/**
 * @Description: 服务变化监听器
 * @Author: Mr.Tong
 */
@Slf4j
public class ServiceChangeListener implements CuratorListener {

    private ReferenceServiceModel referenceServiceModel;

    public ServiceChangeListener(ReferenceServiceModel referenceServiceModel) {
        this.referenceServiceModel = referenceServiceModel;
    }

    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        referenceServiceModel.getReferences();
    }


//    private String referenceServiceName;


//    public ServiceChangeListener(String referenceServiceName) {
//        this.referenceServiceName = referenceServiceName;
//    }

    /**
     * @Description: 服务变化时回调此函数
     */
//    @Override
//    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
//        log.info("has been listen to service changes and the reference service name is {}", referenceServiceName);
//        ReferenceServiceUtil.get(referenceServiceName).getReferences();
//    }

}
