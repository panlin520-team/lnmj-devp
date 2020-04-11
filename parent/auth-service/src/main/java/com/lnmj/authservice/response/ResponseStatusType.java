package com.lnmj.authservice.response;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 10:27
 * @Description:
 */
//Response状态
public class ResponseStatusType {
    private int Ack;
    private Error Error;
    private String Message;
    private String Timestamp;
    private String Version;

    public int getAck() {
        return Ack;
    }

    public void setAck(int ack) {
        Ack = ack;
    }

    public Error getError() {
        return Error;
    }

    public void setError(Error error) {
        Error = error;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    @Override
    public String toString() {
        return "ResponseStatusType{" +
                "Ack=" + Ack +
                ", Error=" + Error +
                ", Message='" + Message + '\'' +
                ", Timestamp='" + Timestamp + '\'' +
                ", Version='" + Version + '\'' +
                '}';
    }
}
