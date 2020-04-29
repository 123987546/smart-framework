package org.smart4j.framework;


import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
import org.smart4j.framework.helper.AopHelper;
import org.smart4j.framework.util.ClassUtil;

/**
 * 加载相应的helper类
 */
public final class HelperLoader {
    /**
     * AopHelper要在IocHelper之前加载
     */
   public static void init(){
       Class<?>[] classList={
               AopHelper.class,
               ClassHelper.class,
               BeanHelper.class,
               IocHelper.class,
               ControllerHelper.class
       };
       for(Class<?> cls:classList){
           ClassUtil.loadClass(cls.getName(),true);
       }
   }

}
