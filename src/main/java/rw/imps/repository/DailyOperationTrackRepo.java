package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.DailyOperationTrack;
import rw.imps.domain.enums.DailyOperationTrackStatus;

import java.time.LocalDate;

@Repository
public interface DailyOperationTrackRepo extends JpaRepository<DailyOperationTrack,Long > {
    DailyOperationTrack findByDailyOperationTrackStatus(boolean status);
    DailyOperationTrack findByDailyCode(String dailyCode);
}
