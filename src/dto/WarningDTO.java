package dto;

import java.sql.Timestamp;

public class WarningDTO {
    private int warningId;
    private String sensorId;
    private String message;
    private Timestamp warningDate;
    private String userId;

    public WarningDTO() {}

    public WarningDTO(int warningId, String sensorId, String message, Timestamp warningDate, String userId) {
        this.warningId = warningId;
        this.sensorId = sensorId;
        this.message = message;
        this.warningDate = warningDate;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WarningDTO [warningId=" + warningId + ", sensorId=" + sensorId + ", message=" + message +
               ", warningDate=" + warningDate + ", userId=" + userId + "]";
    }

    // getter & setter
    public int getWarningId() { return warningId; }
    public void setWarningId(int warningId) { this.warningId = warningId; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getWarningDate() { return warningDate; }
    public void setWarningDate(Timestamp warningDate) { this.warningDate = warningDate; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}

	
	
	
	
	
	
	

