package com.stephenshen.ssregistry;

import com.stephenshen.ssregistry.cluster.Cluster;
import com.stephenshen.ssregistry.cluster.Server;
import com.stephenshen.ssregistry.model.InstanceMeta;
import com.stephenshen.ssregistry.service.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Rest controller for registry.
 * @author stephenshen
 * @date 2024/5/14 07:47:52
 */
@Slf4j
@RestController
public class SSRegistryController {

    @Autowired
    private RegistryService registryService;
    @Autowired
    private Cluster cluster;

    @RequestMapping("/reg")
    public InstanceMeta register(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info(" ===> register: {} @ {}", service, instance);
        return registryService.register(service, instance);
    }

    @RequestMapping("/unreg")
    public InstanceMeta unregister(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info(" ===> unregister: {} @ {}", service, instance);
        return registryService.unregister(service, instance);
    }

    @RequestMapping("/findAll")
    public List<InstanceMeta> findAllInstances(@RequestParam String service) {
        log.info(" ===> findAllInstances: {}", service);
        return registryService.getAllInstances(service);
    }

    @RequestMapping("/renew")
    public long renew(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info(" ===> renew: {} @ {}", service, instance);
        return registryService.renew(instance, service);
    }

    @RequestMapping("/renews")
    public long renews(@RequestParam String services, @RequestBody InstanceMeta instance) {
        log.info(" ===> renews: {} @ {}", services, instance);
        return registryService.renew(instance, services.split(","));
    }

    @RequestMapping("/version")
    public Long version(@RequestParam String service) {
        log.info(" ===> version: {}", service);
        return registryService.version(service);
    }

    @RequestMapping("/versions")
    public Map<String, Long> versions(@RequestParam String services) {
        log.info(" ===> versions: {}", services);
        return registryService.versions(services.split(","));
    }

    @RequestMapping("/info")
    public Server info() {
        log.info(" ===> info: {}", cluster.self());
        return cluster.self();
    }

    @RequestMapping("/cluster")
    public List<Server> cluster() {
        log.info(" ===> cluster: {}", cluster.getServers());
        return cluster.getServers();
    }

    @RequestMapping("/leader")
    public Server leader() {
        log.info(" ===> leader: {}", cluster.leader());
        return cluster.leader();
    }

    @RequestMapping("/sl")
    public Server sl() {
        cluster.self().setLeader(true);
        log.info(" ===> leader: {}", cluster.self());
        return cluster.self();
    }
}
