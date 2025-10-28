package service;

public interface MqttPubSubService {
    void start();  // 메시지 수신 시작
    void close();  // 구독 종료
    void setMessageListener(MessageListener listener); // 메시지 수신 콜백 등록

    interface MessageListener {
        void onMessageReceived(String message);
    }
}
