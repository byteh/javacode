
 
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

