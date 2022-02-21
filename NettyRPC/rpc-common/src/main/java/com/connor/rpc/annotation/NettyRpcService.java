package com.connor.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 使用注解标注要发布的服务
 * @Author: connor
 */

/**
 * 标明该注解可以用于类、接口（包括注解类型）或enum声明
 */
@Target({ElementType.TYPE})
/**
 * 定义生命周期，注解信息将在运行期(JVM)也保留，因此可以通过反射机制读取注解的信息
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * 把普通pojo实例化到spring容器中，相当于配置文件中的 <bean id="" class=""/>
 */
@Component
public @interface NettyRpcService {
    /**
     * 获取注解类的类名.call
     * @return
     */
    Class<?> value();

    /**
     * 区分重载
     * @return
     */
    String version() default "";
}
