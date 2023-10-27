package com.ouc.rpc.framework.rmiprovider.app;

import com.ouc.rpc.framework.api.performance.StrService;
import com.ouc.rpc.framework.rmiprovider.api.StrServiceRMIImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class ProviderRMIApplication {


    public static void main(String[] args) {

        try {
            StrService strService = new StrServiceRMIImpl();
            LocateRegistry.createRegistry(2000);
            Naming.rebind("rmi://localhost:2000/str", strService);
            System.out.println("start server,port is 2000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
