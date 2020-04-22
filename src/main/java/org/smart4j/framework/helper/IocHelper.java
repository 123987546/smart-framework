package org.smart4j.framework.helper;




import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class IocHelper {
    static {
        //获取所有Bean类与Bean实例的映射
        Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            //遍历BeanMap
            for(Map.Entry<Class<?>,Object> beanEntity:beanMap.entrySet()){
                //获取bean和bean实例
                Class<?> beanClass=beanEntity.getKey();
                Object beanInstance=beanEntity.getValue();
                Field[] beanFields=beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)){
                    //遍历field域
                    for(Field beanField:beanFields){
                        //判断当前域是否带有@Inject注解
                        if(beanField.isAnnotationPresent(Inject.class)){
                            //从beanMap中获得相应的实例
                            Class<?> beanFieldClass=beanField.getType();
                            Object beanFeildInstance=beanMap.get(beanFieldClass);
                            if(beanFeildInstance!=null){
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance,beanField,beanFeildInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
