package com.hmall.gateway.route;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouteLoader {
    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter routeDefinitionWriter;

    // 保存更新过的路由id
    private final Set<String> routeIds = new HashSet<>();

    private final String dataId = "gateway-routes.json";
    private final String group = "DEFAULT_GROUP";

    @PostConstruct // 依赖注入完成后、Bean 正式投入使用前自动执行一次
    public void initRouteConfigListener() throws NacosException {
        // 1.注册监听器并首次拉取配置
        ConfigService configService = nacosConfigManager.getConfigService();

        String configInfo = configService.getConfigAndSignListener(dataId, group, 5000, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                updateConfigInfo(configInfo);
            }
        });
        // 2.首次启动时，更新一次配置
        updateConfigInfo(configInfo);
    }

    private void updateConfigInfo(String configInfo) {
        log.debug("监听到路由配置更新：{}", configInfo);
        List<RouteDefinition> routeDefinitionList = JSONUtil.toList(configInfo, RouteDefinition.class);
        // 先进行删除
        for (String routeId : routeIds) {
            routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
        }
        routeIds.clear();
        if (CollUtil.isEmpty(routeDefinitionList)) {
            // 路由配置为空
            return;
        }

        for (RouteDefinition routeDefinition : routeDefinitionList) {
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            routeIds.add(routeDefinition.getId()); // 添加路由ID到集合中
        }
    }
}
