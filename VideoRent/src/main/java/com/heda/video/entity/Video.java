package com.heda.video.entity;

public class Video {
    private Integer videoId;

    private String videoNo;

    private String videoName;

    private String director;

    private String actor;

    private String type;

    private String year;

    private Integer count;

    private Integer currentCount;

    private String price;

    private String videoUrl;

    private String desc;

    private String status;

    private String rentTimes;

    private String position;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoNo() {
        return videoNo;
    }

    public void setVideoNo(String videoNo) {
        this.videoNo = videoNo == null ? null : videoNo.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor == null ? null : actor.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRentTimes() {
        return rentTimes;
    }

    public void setRentTimes(String rentTimes) {
        this.rentTimes = rentTimes == null ? null : rentTimes.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}