package ru.mystudy;

import java.time.LocalDateTime;

public class UtilsValueMap {
    private final Object result;
    private LocalDateTime expiredTime;
    private final long liveTime;

    public UtilsValueMap(Object result, long liveTime) {
        this.result = result;
        this.liveTime = liveTime;
        updateExpiredTime();
    }

    public void updateExpiredTime() {
        if (liveTime == 0) return;
        expiredTime = LocalDateTime.now().plusNanos(1000000L * liveTime);
    }

    public Object getResult() {
        return result;
    }

    public boolean isExpired() {
        if (liveTime == 0) return false;
        return LocalDateTime.now().isAfter(expiredTime);
    }
}
