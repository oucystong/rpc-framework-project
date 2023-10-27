package com.ouc.rpc.framework.api.service;

import java.util.List;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public interface AuthorPublicationPriceService {

    public List<String> getPublicationAndPrice(String authorName);
}
