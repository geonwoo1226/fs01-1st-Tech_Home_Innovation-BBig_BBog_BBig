package Controller;

import java.util.Arrays;
import java.util.List;

public class SensorControlPreview {

    public static void main(String[] args) {
        // 방과 roomNum 매핑
        String[][] rooms = {
                {"room1", "101/3002"},
                {"room2", "101/3001"},
                {"living", "201/5001"},
                {"kitchen", "201/5002"}
        };

        for (String[] room : rooms) {
            String roomName = room[0];
            String roomNum = room[1];

            List<String> sensors;

            switch (roomName) {
                case "room1":
                case "room2":
                    sensors = Arrays.asList("LED", "curtain");
                    break;
                case "living":
                    sensors = Arrays.asList("LED", "pump");
                    break;
                case "kitchen":
                    sensors = Arrays.asList("LED");
                    break;
                default:
                    sensors = Arrays.asList();
            }

            for (String sensor : sensors) {
                for (String action : Arrays.asList("ON", "OFF")) {
                    String address = roomNum + "/";
                    String message = roomName + "/" + sensor + "_" + action;
                    System.out.println("[센서 제어 pub]");
                    System.out.println("MQTT 전송 -> Topic: home/" + address + ", Message: " + message);
                    System.out.println("---------------------------------------------------");
                    System.out.println("[lcd 출력 pub]");
                    System.out.println("Topic: home/lcd -> Message: " + message + "/executing");
                    System.out.println("=======================================================");
                    System.out.println();
                }
            }
        }
    }
}
