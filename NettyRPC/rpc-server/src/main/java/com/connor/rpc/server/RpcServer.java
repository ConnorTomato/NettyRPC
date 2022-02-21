package com.connor.rpc.server;

import com.connor.rpc.annotation.NettyRpcService;
import com.connor.rpc.server.core.NettyServer;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @Description: RpcServer在NettyServer基础上，主要解析注解，服务在启动的时候扫描得到所有的服务接口及其实现：
 * @Author: connor
 */
public class RpcServer extends NettyServer implements ApplicationContextAware, InitializingBean, DisposableBean {

    public RpcServer(String serverAddress, String registryAddress) {
        super(serverAddress, registryAddress);
    }

    /**
     * 服务在启动的时候扫描得到所有的服务接口及其实现：
     * @param ctx
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(NettyRpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                // 获取所有带@RpcService注解的类的bean名称和bean对象
                NettyRpcService nettyRpcService = serviceBean.getClass().getAnnotation(NettyRpcService.class);
                String interfaceName = nettyRpcService.value().getName();
                String version = nettyRpcService.version();
                super.addService(interfaceName, version, serviceBean);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.start();
    }

    @Override
    public void destroy() {
        super.stop();
    }
}
