package it.polimi.two.weiava.models;


/**
 * Created by Wei on 25/03/18.
 */

public class Schedule {
    private String id;
    private Long timestamp;
    private String qType;
    private Integer score;

    public Schedule() {
        score = -1;
    }

    public Schedule(Long timestamp, String qType) {
        this.timestamp = timestamp;
        this.qType = qType;
        score = -1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getqType() {
        return qType;
    }

    public Integer getScore() {
        return score;
    }

    public void setqType(String qType) {
        this.qType = qType;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
