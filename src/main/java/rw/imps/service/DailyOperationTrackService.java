package rw.imps.service;

import rw.imps.domain.DailyOperationTrack;

import java.util.List;

public interface DailyOperationTrackService {
    public void openTodayOperationTrack() throws Exception;
    public DailyOperationTrack updateTodayOperationTrack(DailyOperationTrack dailyOperationTrack, Long id) throws Exception;
    public String closeTodayOperationTrack() throws Exception;
    public  DailyOperationTrack findOne(Long id);
    public List<DailyOperationTrack> dailyOperationTracks();
}
