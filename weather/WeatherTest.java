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
