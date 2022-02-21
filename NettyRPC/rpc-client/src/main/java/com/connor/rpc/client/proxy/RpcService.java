package com.connor.rpc.client.proxy;

import com.connor.rpc.client.handler.RpcFuture;

public interface RpcService<T, P, FN extends SerializableFunction<T>> {
    RpcFuture call(String funcName, Object... args) throws Exception;

    /**
     * lambda method reference
     */
    RpcFuture call(FN fn, Object... args) throws Exception;

}
