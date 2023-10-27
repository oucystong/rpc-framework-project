package com.ouc.rpc.framework.dubboprovider.api;

import com.ouc.rpc.framework.api.service.NovelAuthorService;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class NovelAuthorServiceDubboImpl implements NovelAuthorService {


    @Override
    public String getAuthor(String novelName) {
        return "Dubbo Prefix: " + novelName + "\n Author: George";
    }
}
