package com.ouc.rpc.framework.rmiprovider.api;

import com.ouc.rpc.framework.api.performance.StrService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class StrServiceRMIImpl extends UnicastRemoteObject implements StrService {

    public StrServiceRMIImpl() throws RemoteException {
    }

    @Override
    public String getRequestString(String requestStr) {
        return requestStr;
    }
}
