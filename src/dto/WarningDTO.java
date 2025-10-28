package dto;

import java.sql.Timestamp;
// warning_id(auto)    room_id      sensor     warning_type   message  date(auto)

public class WarningDTO {
    private int warningId;
    private int room_id;
    private String user_id;
    private String sensor;
    private String warningType;
    private String message;
    private String date;        
    private String phoneNumber;    

    public WarningDTO() {}


	public WarningDTO(int room_id, String user_id, String sensor, String warningType, String date,
			String phoneNumber) {
		super();
		this.room_id = room_id;
		this.user_id = user_id;
		this.sensor = sensor;
		this.warningType = warningType;
		this.date = date;
		this.phoneNumber = phoneNumber;
	}


	public WarningDTO(int warningId, int room_id, String user_id, String sensor, String warningType, String message,
			String date, String phoneNumber) {
		super();
		this.warningId = warningId;
		this.room_id = room_id;
		this.user_id = user_id;
		this.sensor = sensor;
		this.warningType = warningType;
		this.message = message;
		this.date = date;
		this.phoneNumber = phoneNumber;
	}


	public WarningDTO(int warningId, int room_id, String user_id, String sensor, String warningType, String date,
			String phoneNumber) {
		super();
		this.warningId = warningId;
		this.room_id = room_id;
		this.user_id = user_id;
		this.sensor = sensor;
		this.warningType = warningType;
		this.date = date;
		this.phoneNumber = phoneNumber;
	}





	public String getUser_id() {
		return user_id;
	}





	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getDate() {
		return date;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}





	public String getPhoneNumber() {
		return phoneNumber;
	}





	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}





	public void setDate(String date) {
		this.date = date;
	}





	public int getWarningId() {
		return warningId;
	}

	public void setWarningId(int warningId) {
		this.warningId = warningId;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}





	@Override
	public String toString() {
		return "WarningDTO [warningId=" + warningId + ", room_id=" + room_id + ", user_id=" + user_id + ", sensor="
				+ sensor + ", warningType=" + warningType + ", date=" + date + ", phoneNumber=" + phoneNumber + "]";
	}


	
}

	
	
	
	
	
	
	

