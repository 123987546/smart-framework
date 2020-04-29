package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 */
public abstract class AspectProxy implements Proxy{
    private static final Logger LOGGER= LoggerFactory.getLogger(AspectProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result=null;
        Class<?> cls=proxyChain.getTargetClass();
        Method method=proxyChain.getTargetMethod();
        Object[] params=proxyChain.getMethodParams();
        begin();
        try{
            if(intercept(cls,method,params)){
                before(cls,method,params);
                result=proxyChain.doProxyChain();
                after(cls,method,params);
            }else{
                result=proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("proxy failure:",e);
            error(cls,method,params,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    /**
     * 以下方法作为钩子方法，我们可以在子类中选择性实现
     *
     */
    public void error(Class<?> cls, Method method, Object[] params, Exception e){

    }

    public void end(){

    }

    public void after(Class<?> cls, Method method, Object[] params){

    }

    public void before(Class<?> cls, Method method, Object[] params){

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params){
        return true;
    }

    public void begin(){

    }


}
