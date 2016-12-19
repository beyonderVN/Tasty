package com.vnwarriors.tastyclarify.model;


/**
 * Created by Alessandro Barreto on 17/06/2016.
 */
public class Chat {

    private String id;
    private UserModel user;
    private String message;
    private String timeStamp;
    private File file;
    private Map map;

    public Chat() {
    }

    public Chat(UserModel user, String message, String timeStamp, File file) {
        this.user = user;
        this.message = message;
        this.timeStamp = timeStamp;
        this.file = file;
    }

    public Chat(UserModel user, String timeStamp, Map map) {
        this.user = user;
        this.timeStamp = timeStamp;
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "map=" + map +
                ", file=" + file +
                ", timeStamp='" + timeStamp + '\'' +
                ", message='" + message + '\'' +
                ", userModel=" + user +
                '}';
    }
}
