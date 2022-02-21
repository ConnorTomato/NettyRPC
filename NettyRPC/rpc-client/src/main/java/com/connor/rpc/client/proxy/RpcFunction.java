package com.connor.rpc.client.proxy;

/**
* @Description: lambda method reference
* @Author: connor
*/
@FunctionalInterface
public interface RpcFunction<T, P> extends SerializableFunction<T> {
    Object apply(T t, P p);
}
