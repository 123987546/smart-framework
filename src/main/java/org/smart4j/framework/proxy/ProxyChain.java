package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链：在类中维持了一个list保存该方法代理链的代理
 * 保存了代理方法的类，方法，参数等
 *
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final  Object[] methodParams;
    private List<Proxy> proxyList=new ArrayList<Proxy>();
    private int proxyIndex=0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if(proxyIndex<proxyList.size()){
            //当proxy没有执行完，就不断调用下一个
            methodResult=proxyList.get(proxyIndex++).doProxy(this);
        }else{
            //当peoxy执行完了，那就直接调用目标对象业务逻辑
            methodResult=methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }
}
