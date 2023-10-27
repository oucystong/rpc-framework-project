package com.ouc.rpc.framework.provider.api;

import com.sun.xml.internal.ws.transport.http.server.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.spi.Provider;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@WebService
public class HelloService {
    @WebMethod
    public String yourMethodOne(@WebParam(name = "input") int input) {
        // Your method implementation
        return "Result";
    }

    @WebMethod
    public @WebResult(name = "Response",partName = "Res") String yourMethodTwo(@WebParam(name = "Pa1",partName = "Pa111") int input1, @WebParam(name = "Pa2",partName = "pa222") int input2) {
        // Your method implementation
        return "Result";
    }

    @WebMethod
    public void yourMethodThree(@WebParam int input1, @WebParam int input2) {
        // Your method implementation
    }

    @WebMethod
    public void yourMethodFour() {
        // Your method implementation
    }

    public static void main(String[] args) {
        // 发布Web服务
//        String address = "http://localhost:8080/helloService";
//        EndpointImpl publish = (EndpointImpl) Endpoint.publish(address, new HelloService());


        // 生成WSDL文档
        //        String wsdlUrl = address + "?wsdl";
        //        System.out.println("WSDL生成成功，访问URL：" + wsdlUrl);
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();

        // 设置服务地址
        jaxWsServerFactoryBean.setAddress("http://localhost:8080/helloService");


        // 设置Web服务实现类
        jaxWsServerFactoryBean.setServiceClass(HelloService.class);


        // 创建服务
        jaxWsServerFactoryBean.create();


    }


}
