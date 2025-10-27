package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import view.DetailView;

public class SensorServiceImpl implements SensorService {
	@Override
	public void saveSensorData(String topic, String message) {
		// TODO Auto-generated method stub
		
	}
	DetailView view = new DetailView();

	public List<String> getSensorByRoom(String roomChoice) {
		List<String> sensors = new ArrayList<>();
		
	    
		while(true) {
			switch(roomChoice) {
			case "방1":
				sensors = Arrays.asList("LED", "커튼");
				break;
			case "방2":
				sensors = Arrays.asList("LED", "커튼");
				break;
			case "거실":
				sensors = Arrays.asList("LED", "화분센서");
				break;
			case "부엌":
				sensors = Arrays.asList("LED");
				break;
			}
			return sensors;
		}
		
	}	

}
