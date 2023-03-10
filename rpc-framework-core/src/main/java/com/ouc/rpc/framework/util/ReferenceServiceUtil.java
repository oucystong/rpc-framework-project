/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ouc.rpc.framework.util;

import com.ouc.rpc.framework.model.ReferenceServiceModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 服务引用工具类
 * @Author: Mr.Tong
 */
public final class ReferenceServiceUtil {

    private static final Map<String, ReferenceServiceModel> referenceServiceModelMap = new ConcurrentHashMap<>();

    private ReferenceServiceUtil() {
    }

    public static void put(ReferenceServiceModel referenceServiceModel) {
        referenceServiceModelMap.put(referenceServiceModel.getReferenceServiceName(), referenceServiceModel);
    }

    public static ReferenceServiceModel get(String referenceServiceName) throws Exception {
        // 从容器中获取引用的服务
        ReferenceServiceModel referenceServiceModel = referenceServiceModelMap.get(referenceServiceName);
        // 服务判断
//        if (referenceServiceModel == null) {
//            synchronized (referenceServiceModelMap) {
//                referenceServiceModel = referenceServiceModelMap.get(referenceServiceName);
//                if (referenceServiceModel == null) {
//                    referenceServiceModel = new ReferenceServiceModel();
//                    referenceServiceModel.setReferenceServiceName(referenceServiceName);
//                    referenceServiceModel.init();
//                    referenceServiceModelMap.put(referenceServiceName, referenceServiceModel);
//                }
//            }
//        }
        return referenceServiceModel;
    }

}
