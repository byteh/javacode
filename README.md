=======
# javacode
learn some java by coding. 

=======
气象局会调用 WeatherBroadcaster （广播站）的 updateWeather方法，广播站需要把天气变化信息发布给听众。听众类型有小猫，小狗，以后还会有更多动物甚至机器人等。

问题如何设计与实现updateWeather方法（不限于updateWeather方法）以达成对扩展开放对修改关闭呢？这其中的问题有哪些呢？

``` java
public interface WeatherUpdate {
   	String typeName();
   	String score();
}

public class WeatherBroadcaster {
	
	public void updateWeather(WeatherUpdate update) {
	     // 通知想收到天气变化的「接受者」
         
	}
}

public class Cat {

    public void miaomiao(String message) {
       //miaomiao +, + wind + , + 4 
    }	
}

public class Dog {

    public void wangwang(String message) {
       //wangwang +, + wind + , + 4
    }	
}
```
------------------------------------------------------------------------------

我的类图如下
![image](https://github.com/byteh/javacode/blob/master/weather/weather.jpg)

WeatherBureau（气象局）通过依赖注入的方式握有抽象类IWeatherPublisher（天气发布渠道），而WeatherBroadcaster（天气广播站）是实现了天气发布能力的渠道之一，如果未来需要了，可以扩展支持其他的发布渠道，例如天气电视台、互联网天气发布等可以实现天气发布能力的。

代码如下

``` java

# 气象局
public class WeatherBureau {
    String state ;
    
    public String getWeather() {
      return state;
    }
    
    public void setWeather(String state) {
      this.state = state;
    }
    
    public void publishWeatherInfoByChannel(IWeatherPublisher weatherPublisher) {
      weatherPublisher.updateWeather(this.getWeather());
    }
}

# 天气发布能力接口
public interface IWeatherPublisher{
   public abstract void updateWeather(String state);
}

# 广播电台
import java.util.ArrayList;
import java.util.List;

public class WeatherBroadcaster implements IWeatherPublisher{
	private List<BroadcastingListener> listeners = new ArrayList<BroadcastingListener>();
 
   public void updateWeather(String state) {
	  String weatherInfo = "From WeatherBroadcaster, The Weaher will be ::: " + state;
      this.notifyAllListener(weatherInfo);
   }

   public void attach(BroadcastingListener listener){
      listeners.add(listener);      
   }
 
   public void notifyAllListener(String message){
      for (BroadcastingListener listener : listeners) {
         listener.onListening(message);
      }
   }  
}

```

广播电台和听众之间，用了一个简单的观察者模式。对天气广播感兴趣的对象，开启电台收听。当接收到气象局发起的天气变更消息后，做出自己的反馈。

抽象出来了一个BroadcastingListener（广播收听者），作为在广播收听订阅的对象，猫猫狗狗之类的继承自BroadcastingListener。当扩展收听者时，广播站并不需要修改，可以做到对收听者的扩展开放，对广播站的修改关闭。

``` java

# 收听者
import java.io.*;

public abstract class BroadcastingListener {
	protected String listenerName;
   	protected WeatherBroadcaster broadcaster;
   	public abstract void startListen(WeatherBroadcaster broadcaster);
   	public abstract void onListening(String msg);
}

# 猫猫

import java.io.*;

public class Cat extends BroadcastingListener {

   public Cat(String listenerName){
      this.listenerName = listenerName;
   }

   	public void startListen(WeatherBroadcaster broadcaster) {
      this.broadcaster = broadcaster;
		this.broadcaster.attach(this);
    }

	public void onListening(String msg) {
		this.makeSound(msg);
    }
    public void makeSound(String message) {
		System.out.println(this.listenerName + "->>> miaomiao "+message);
    }
	
}

# 狗、机器人等类似
# ……

```

现实生活中肯定比这个复杂，比如广播是通过无线电信号发布的，不会去存储具体的订阅者，而是由设备接收器（例如收音机）去接受信息流。所以我在收听者中用startListen方法来表达这个意思。

调用的main代码
``` java
import java.io.*;

public class WeatherTest{
 
   public static void main(String[] args){
		WeatherBroadcaster weatherBroadcaster =new WeatherBroadcaster();

		Cat catOne = new Cat("3mao");
		catOne.startListen(weatherBroadcaster);

		Dog dogOne = new Dog("OneCai");
		dogOne.startListen(weatherBroadcaster);

		Dog dog2 = new Dog("2gouzi");
		dog2.startListen(weatherBroadcaster);
		
		WeatherBureau weatherBureau = new WeatherBureau();
		weatherBureau.setWeather("cloud , wind 4, temperature 28°.");
		weatherBureau.publishWeatherInfoByChannel(weatherBroadcaster);

		weatherBureau.setWeather("rain , wind 6, temperature 10°.");
		weatherBureau.publishWeatherInfoByChannel(weatherBroadcaster);

   }
}

```

输出如下：
``` shell
➜  weather javac *.java
➜  weather java WeatherTest
3mao->>> miaomiao From WeatherBroadcaster, The Weaher will be ::: cloud , wind 4, temperature 28°.
OneCai->>> wangwang From WeatherBroadcaster, The Weaher will be ::: cloud , wind 4, temperature 28°.
2gouzi->>> wangwang From WeatherBroadcaster, The Weaher will be ::: cloud , wind 4, temperature 28°.
3mao->>> miaomiao From WeatherBroadcaster, The Weaher will be ::: rain , wind 6, temperature 10°.
OneCai->>> wangwang From WeatherBroadcaster, The Weaher will be ::: rain , wind 6, temperature 10°.
2gouzi->>> wangwang From WeatherBroadcaster, The Weaher will be ::: rain , wind 6, temperature 10°.
```

