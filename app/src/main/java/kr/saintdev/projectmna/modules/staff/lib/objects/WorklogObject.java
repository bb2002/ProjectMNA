package kr.saintdev.projectmna.modules.staff.lib.objects;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-09
 */

public class WorklogObject {
    private String workId = null;
    private String date = null;
    private String workStartTime = null;
    private String workEndTime = null;
    private boolean isNowWorking = false;


    public WorklogObject(String workId, String date, String workStartTime, String workEndTime, boolean isNowWorking) {
        this.workId = workId;
        this.date = date;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.isNowWorking = isNowWorking;
    }

    public String getWorkId() {
        return workId;
    }

    public String getDate() {
        return date;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public boolean isNowWorking() {
        return isNowWorking;
    }
}
