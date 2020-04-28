public class HelloProxy {
    private Hello hello;
    public HelloProxy(){
        hello=new HelloImpl();
    }
    public void say(){
        before();
        hello.say();
        after();
    }
    private void after() {
        System.out.println("before");
    }
    private void before() {
        System.out.println("after");
    }
}
