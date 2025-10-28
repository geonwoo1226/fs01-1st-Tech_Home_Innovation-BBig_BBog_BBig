package service;

import java.util.List;

import dto.WarningDTO;

public interface WarningService {
    // 방/유저 기반 경고 저장
    List<WarningDTO> viewWarning(int roomId, int userId, String sensor, String warningType, String message);

    // 토픽/페이로드 기반 경고 저장
    void saveWarning(String topic, String payload);
}
