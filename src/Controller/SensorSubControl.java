package Controller;

import dto.UserDTO;
import service.MqttPubSubService;
import service.MqttPubSubServiceImpl;
import service.WarningService;
import service.WarningServiceImpl;

public class SensorSubControl {

    private static Thread mqttThread; // MQTT 수신용 스레드
    private static boolean running = false; // 스레드 실행 상태 관리

    public SensorSubControl() {}

    // ✅ 로그인한 사용자별 MQTT 구독 (항상 대기)
    public static void subscribeById(String userId) {
        if (running) {
            System.out.println("⚠️ MQTT 구독이 이미 실행 중입니다.");
            return;
        }

        running = true;

        mqttThread = new Thread(() -> {
            System.out.println("🚀 MQTT Subscribe Thread started for user: " + userId);

            UserDTO user = new UserDTO();
            user.setUserId(userId);

            MqttPubSubService pubsub = new MqttPubSubServiceImpl(user);

            try {
                while (running) {
                    Thread.sleep(1000); // 1초마다 대기 (CPU 부하 방지)
                }
            } catch (InterruptedException e) {
                System.out.println("🧵 MQTT Subscribe Thread interrupted.");
            } finally {
                pubsub.close();
                System.out.println("🛑 MQTT Subscribe Thread stopped.");
            }
        });

        mqttThread.start();
    }

    // ✅ 아이디 없이 전체 구독 (항상 대기)
    public void subscribe() {
        if (running) {
            System.out.println("⚠️ MQTT 구독이 이미 실행 중입니다.");
            return;
        }

        running = true;

        mqttThread = new Thread(() -> {
            System.out.println("🚀 MQTT Subscribe Thread started (default user).");

            UserDTO user = new UserDTO();
            user.setUserId("default_user");

            MqttPubSubService pubsub = new MqttPubSubServiceImpl(user);
            

            try {
                while (running) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("🧵 MQTT Subscribe Thread interrupted.");
            } finally {
                pubsub.close();
                System.out.println("🛑 MQTT Subscribe Thread stopped.");
            }
        });

        mqttThread.start();
    }
    
    // ✅ 아이디 없이 전체 구독 (항상 대기)
    public static void staticSubscribe() {
        if (running) {
            System.out.println("⚠️ MQTT 구독이 이미 실행 중입니다.");
            return;
        }

        running = true;

        mqttThread = new Thread(() -> {
            System.out.println("🚀 MQTT Subscribe Thread started (default user).");

            UserDTO user = new UserDTO();
            user.setUserId("default_user");

            MqttPubSubService pubsub = new MqttPubSubServiceImpl(user);

            try {
                while (running) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("🧵 MQTT Subscribe Thread interrupted.");
            } finally {
                pubsub.close();
                System.out.println("🛑 MQTT Subscribe Thread stopped.");
            }
        });

        mqttThread.start();
    }

    // ✅ MQTT 구독 종료
    public void stopSubscription() {
        if (!running) {
            System.out.println("⚠️ 현재 실행 중인 구독이 없습니다.");
            return;
        }

        System.out.println("🧩 MQTT 구독 중단 요청...");
        running = false;

        if (mqttThread != null) {
            mqttThread.interrupt();
        }
    }
    
    // ✅ MQTT 구독 종료
    public static void staticStopSubscription() {
        if (!running) {
            System.out.println("⚠️ 현재 실행 중인 구독이 없습니다.");
            return;
        }

        System.out.println("🧩 MQTT 구독 중단 요청...");
        running = false;

        if (mqttThread != null) {
            mqttThread.interrupt();
        }
    }
}
