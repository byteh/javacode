import java.io.*;

public abstract class BroadcastingListener {
	protected String listenerName;
   	protected WeatherBroadcaster broadcaster;
   	public abstract void startListen(WeatherBroadcaster broadcaster);
   	public abstract void onListening(String msg);
}
