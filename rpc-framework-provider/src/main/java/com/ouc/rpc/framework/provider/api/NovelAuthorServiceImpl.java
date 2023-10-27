package com.ouc.rpc.framework.provider.api;

import com.ouc.rpc.framework.api.service.NovelAuthorService;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @Author: Mr.Tong
 */
@Service
public class NovelAuthorServiceImpl implements NovelAuthorService {

    @Override
    public String getAuthor(String novelName) {

        return "sRPC Prefix: " + novelName + "\n Author: George";
    }
}
