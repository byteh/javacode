// import java.io.*;
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