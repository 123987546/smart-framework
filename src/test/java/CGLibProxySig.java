import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxySig implements MethodInterceptor {
    private static CGLibProxySig instance=new CGLibProxySig();
    private CGLibProxySig(){
    }
    public static CGLibProxySig getInstance(){
        return instance;
    }
    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object result=proxy.invokeSuper(obj,args);
        after();
        return result;
    }
    private void after() {
        System.out.println("before");
    }
    private void before() {
        System.out.println("after");
    }
}
