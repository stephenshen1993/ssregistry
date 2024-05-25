package com.stephenshen.ssregistry.cluster;

import com.stephenshen.ssregistry.http.HttpInvoker;
import com.stephenshen.ssregistry.service.SSRegistryService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * check health for servers.
 * @author stephenshen
 * @date 2024/5/24 07:55:08
 */
@Slf4j
public class ServerHealth {

    final Cluster cluster;

    public ServerHealth(Cluster cluster) {
        this.cluster = cluster;
    }

    final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    long timeout = 5_000;

    public void checkServerHealth() {
        executor.scheduleWithFixedDelay( () -> {
            try {
                updateServers(); // 1、更新服务状态
                doElect();   // 2、选主
                syncSnapshotFromLeader(); // 3、同步快照

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 0, timeout, TimeUnit.MILLISECONDS);
    }

    private void doElect() {
        new Election().electLeader(cluster.getServers());
    }

    private void syncSnapshotFromLeader() {
        Server self = cluster.self();
        Server leader = cluster.leader();
        log.debug(" ===>>> leader version: " + leader.getVersion() + ", my version: " + self.getVersion());
        if (!self.isLeader() && self.getVersion() < leader.getVersion()) {
            log.debug(" ===>>> sync snapshot from leader: " + leader);
            Snapshot snapshot = HttpInvoker.httpGet(leader.getUrl() + "/snapshot", Snapshot.class);
            log.debug(" ===>>> sync snapshot: " + snapshot);
            SSRegistryService.restore(snapshot);
        }
    }



    private void updateServers() {
        List<Server> servers = cluster.getServers();
        servers.stream().parallel().forEach(server -> {
            try {
                if (server.equals(cluster.self())) return; // 如果是自己，则不去更新状态
                Server serverInfo = HttpInvoker.httpGet(server.getUrl() + "/info", Server.class);
                log.debug(" ===>>> health check success for " + serverInfo);
                if (serverInfo != null) {
                    server.setStatus(true);
                    server.setLeader(serverInfo.isLeader());
                    server.setVersion(serverInfo.getVersion());
                }
            } catch (Exception e) {
                log.debug(" ===>>> health check failed for " + server.getUrl());
                server.setStatus(false);
                server.setLeader(false);
            }
        });
    }
}
