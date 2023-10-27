package com.ouc.rpc.framework.api.performance;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Description: 性能测试服务
 * @Author: Mr.Tong
 */
public interface StrService extends Remote {
    String getRequestString(String requestStr) throws RemoteException;
}
