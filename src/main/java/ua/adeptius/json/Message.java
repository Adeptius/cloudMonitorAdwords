package ua.adeptius.json;


public class Message {

    public Message(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    private Status status;
    private String message;


    public enum Status {
        Success, Error
    }


    @Override
    public String toString() {
        return "{\"Status\":\"" + status + "\",\"Message\":\"" + message + "\"}";
    }

}
