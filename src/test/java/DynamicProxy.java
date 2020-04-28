import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler{
    private Object target;
    public DynamicProxy(Object hello){
        target=hello;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result=method.invoke(target,args);
        after();
        return result;
    }
    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
    private void after() {
        System.out.println("before");
    }
    private void before() {
        System.out.println("after");
    }

}
