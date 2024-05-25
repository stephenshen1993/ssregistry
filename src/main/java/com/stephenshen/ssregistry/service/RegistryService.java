package com.stephenshen.ssregistry.service;

import com.stephenshen.ssregistry.model.InstanceMeta;

import java.util.List;
import java.util.Map;

/**
 * Interface of registry service
 *
 * @author stephenshen
 * @date 2024/5/10 07:01:51
 */
public interface RegistryService {

    // 最基础的三个方法
    InstanceMeta register(String service, InstanceMeta instance);

    InstanceMeta unregister(String service, InstanceMeta instance);

    List<InstanceMeta> getAllInstances(String service);

    // todo 添加一些高级功能
    long renew(InstanceMeta instance, String... services);
    Long version(String service);
    Map<String, Long> versions(String... services);
}
