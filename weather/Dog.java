import java.io.*;

public class Dog extends BroadcastingListener {

   public Dog(String listenerName){
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
		  System.out.println(this.listenerName + "->>> wangwang "+message);
    }
	
}
