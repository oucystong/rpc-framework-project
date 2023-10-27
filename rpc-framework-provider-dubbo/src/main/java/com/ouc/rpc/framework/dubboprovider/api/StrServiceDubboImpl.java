package com.ouc.rpc.framework.dubboprovider.api;

import com.ouc.rpc.framework.api.performance.StrService;

import java.rmi.RemoteException;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class StrServiceDubboImpl implements StrService {

    @Override
    public String getRequestString(String requestStr) throws RemoteException {
        return requestStr;
    }
}
