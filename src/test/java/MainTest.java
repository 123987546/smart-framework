import org.junit.Test;

import java.lang.reflect.Proxy;

public class MainTest {

    @Test
    public void test1(){
        HelloProxy helloProxy=new HelloProxy();
        helloProxy.say();
    }
    @Test
    public void test2(){
        Hello hello=new HelloImpl();
        DynamicProxy dynamicProxy=new DynamicProxy(hello);
        Hello helloProxy= (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),hello.getClass().getInterfaces(),dynamicProxy);
        helloProxy.say();
    }

    @Test
    public void test3(){
        Hello hello=new HelloImpl();
        DynamicProxy dynamicProxy=new DynamicProxy(hello);
        Hello helloProxy=dynamicProxy.getProxy();
        helloProxy.say();
    }
    @Test
    public void test4(){
        CGLIBProxy cglibProxy=new CGLIBProxy();
        Hello helloProxy=cglibProxy.getProxy(HelloImpl.class);
        helloProxy.say();
    }

    @Test
    public void test5(){
        Hello helloProxy=CGLibProxySig.getInstance().getProxy(HelloImpl.class);
        helloProxy.say();
    }

}
