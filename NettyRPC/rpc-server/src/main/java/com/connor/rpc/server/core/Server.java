package com.connor.rpc.server.core;

/**
 * @Description: 定义 Server 启动和结束方法
 * @Author: connor
 */
public abstract class Server {
    /**
     * start server
     * @throws Exception
     */
    public abstract void start() throws Exception;

    /**
     * stop server
     * @throws Exception
     */
    public abstract void stop() throws Exception;

}
