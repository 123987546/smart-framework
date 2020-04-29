package org.smart4j.framework.helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(AopHelper.class);
    /**
     * 通过整个静态块来初始化整个AOP框架
     */
    static {
        try{
            Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap=createTargetMap(proxyMap);
            for(Map.Entry<Class<?>, List<Proxy>> targetEntry:targetMap.entrySet()){
                Class<?> targetClass=targetEntry.getKey();
                List<Proxy> proxyList=targetEntry.getValue();
                Object proxy= ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch (Exception e){
            LOGGER.error("aop failure",e);
        }
    }
    /**
     * 获取Aspect注解中的设置的注解类(也就是切点的类)
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect)throws Exception{
        Set<Class<?>> targetClassSet=new HashSet<Class<?>>();
        Class<? extends Annotation> annotation =aspect.value();
        if(annotation!=null&&!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }


    /**
     * 获取普通功能切片
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        //获取所有切面类
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
        //筛选出所有带有@Aspect注解的类,将注解中的值（一群类）与被注解的类进行映射
        for(Class<?> proxyClass:proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect=proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> transactionClassSet=ClassHelper.getClassSetByAnnotation(Transaction.class);
        proxyMap.put(TransactionProxy.class,transactionClassSet);
    }
    /**
     * 获取
     * @return 代理-被代理列表 map
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap()throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);

        return proxyMap;
    }



    /**
     * 将 代理-被代理列表 map转换为 被代理-代理实例列表 map
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap( Map<Class<?>,Set<Class<?>>> proxyMap)throws Exception{
        Map<Class<?>, List<Proxy>> targetMap=new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
            Class<?> proxyClass=proxyEntry.getKey();
            Set<Class<?>> targetClassSet=proxyEntry.getValue();
            for(Class<?> targetClass : targetClassSet){
                Proxy proxy=(Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList=new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }
}
