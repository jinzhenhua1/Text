1、BaseActivity 中的 presenter 是用dagger注入的，要确保注入准确，所以需要为每个activity都创建一个@Component 以及 @Module来注入。
此时persenter中需要的公共的参数都可以在 baseComponent中注入，比如一些工具类等.

2、网络请求时，返回的最外层的结构体要继承类ReturnBean。并在 getData 方法中返回真实的数据bean ，如：
~~~
public class HttpRespondData<T> extends ReturnBean<T> {
    private T data;
    private int status = 0;
    private String desc = "";

    @Override
    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
~~~