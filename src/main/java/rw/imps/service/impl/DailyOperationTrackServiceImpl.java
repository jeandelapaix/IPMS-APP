package rw.imps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.imps.domain.AgentTransaction;
import rw.imps.domain.DailyOperationTrack;
import rw.imps.repository.DailyOperationTrackRepo;
import rw.imps.service.AgentTransactionService;
import rw.imps.service.DailyOperationTrackService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DailyOperationTrackServiceImpl implements DailyOperationTrackService {

    @Autowired
    private DailyOperationTrackRepo dailyOperationTrackRepo;

    @Autowired
    private AgentTransactionService agentTransactionService;

    @Override
    public void openTodayOperationTrack() throws Exception {
        DailyOperationTrack dailyOperationTrack = new DailyOperationTrack();
        if (dailyOperationTrackRepo.findByDailyOperationTrackStatus(true) == null) {
            dailyOperationTrack.setCloseAmount(0);
            dailyOperationTrack.setDailyCode(LocalDate.now().toString());
            dailyOperationTrack.setTodayDate(new Date());
            LocalDate previousDate = LocalDate.now().minusDays(1);
            DailyOperationTrack previousDay = dailyOperationTrackRepo.findByDailyCode(previousDate.toString());
            if (previousDay != null) {
                dailyOperationTrack.setStartAmount(previousDay.getCloseAmount());
            } else {
                dailyOperationTrack.setCloseAmount(0);
            }
            dailyOperationTrack.setDailyOperationTrackStatus(true);
            dailyOperationTrackRepo.save(dailyOperationTrack);
        }

    }

    @Override
    public DailyOperationTrack updateTodayOperationTrack(DailyOperationTrack dailyOperationTrack, Long id) throws Exception {

        DailyOperationTrack found = dailyOperationTrackRepo.findById(id).get();
        if (found == null) {
            throw new Exception("Unable to find the track");
        }
        found.setStartAmount(dailyOperationTrack.getStartAmount());
        return dailyOperationTrackRepo.save(found);
    }


    /**
     * make summation for the transactions done today and make it closing amount for today.
     * Not done yet.
     */
    @Override
    public String closeTodayOperationTrack() throws Exception {
        List<AgentTransaction> todayTransactions = agentTransactionService.dailyTransactions();
        double closingAmount = todayTransactions.stream().mapToDouble(e -> e.getNegotiablePrice()).sum();
        DailyOperationTrack dailyOperationTrack = dailyOperationTrackRepo.findByDailyOperationTrackStatus(true);
        if (dailyOperationTrack == null) {
            throw new Exception("Daily Operation for today not found in the system");
        }
        dailyOperationTrack.setCloseAmount(closingAmount);
        dailyOperationTrack.setDailyOperationTrackStatus(false);
        return "Action is done successfully";
    }

    @Override
    public DailyOperationTrack findOne(Long id) {
        Optional<DailyOperationTrack> foundTrack = dailyOperationTrackRepo.findById(id);
        return foundTrack.get();
    }

    @Override
    public List<DailyOperationTrack> dailyOperationTracks() {
        return dailyOperationTrackRepo.findAll();
    }
}
