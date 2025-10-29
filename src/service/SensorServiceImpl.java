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
			case "room1":
				sensors = Arrays.asList("LED", "window");
				break;
			case "room2":
				sensors = Arrays.asList("LED", "window");
				break;
			case "living":
				sensors = Arrays.asList("LED", "pump");
				break;
			case "kitchen":
				sensors = Arrays.asList("LED");
				break;
			}
			return sensors;
		}
		
	}	

}
