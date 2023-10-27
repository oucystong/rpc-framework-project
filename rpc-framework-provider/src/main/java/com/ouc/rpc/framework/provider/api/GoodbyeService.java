package com.ouc.rpc.framework.provider.api;

import org.apache.cxf.BusException;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.wsdl11.ServiceWSDLBuilder;
import org.apache.cxf.wsdl11.WSDLManagerImpl;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@WebService
public class GoodbyeService {
    @WebMethod
    public String goodbyeMethod1(int input) {
        // Your method implementation
        return "Result";
    }

    @WebMethod
    public String goodbyeMethod2(int input1, int input2) {
        // Your method implementation
        return "Result";
    }


}
