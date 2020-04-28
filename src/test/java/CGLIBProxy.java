import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CGLIBProxy implements MethodInterceptor {
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object result=proxy.invokeSuper(obj,args);
        after();
        return result;
    }
    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }
    private void after() {
        System.out.println("before");
    }
    private void before() {
        System.out.println("after");
    }
}
