package com.ouc.rpc.framework.provider.api;

import com.ouc.rpc.framework.api.ProviderService;
import org.springframework.stereotype.Service;

/**
 * @Description: 服务生产者提供的服务实现
 * @Author: Mr.Tong
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String ServiceAMethod(String str) {
        return "ServiceAMethod Execute >> Param >> " + str;
    }

    @Override
    public String ServiceBMethod(String str1, String str2) {
        return "ServiceAMethod Execute >> ParamA >> " + str1 + " ParamB >> " + str2;
    }
}
