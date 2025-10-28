package Controller;

import service.WarningService;
import service.WarningServiceImpl;

public class SensorSubControlTest {
    public static void main(String[] args) throws InterruptedException {

        // 1️ 로그인한 사용자 ID로 MQTT 구독 시작
        //SensorSubControl.subscribeById("user123");
        
        // 1️ 바로 MQTT 구독 시작
        SensorSubControl.staticSubscribe();
        
        

        // 2️ 30초 동안 대기 (MQTT 메시지 수신 중)
        Thread.sleep(30000);

        // 3️ 구독 종료
        SensorSubControl.staticStopSubscription();
    }
}

