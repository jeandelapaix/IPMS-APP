package rw.imps.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.imps.domain.DailyOperationTrack;
import rw.imps.service.DailyOperationTrackService;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dailyopertiontracks")
@CrossOrigin
public class DailyOpertionTrackController {

    @Autowired
    private DailyOperationTrackService dailyOperationTrackService;

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> updateTodayOperationTrack(@RequestBody @Valid DailyOperationTrack dailyOperationTrack, BindingResult result, @PathVariable Long id) throws Exception {
        if (result.hasErrors()) {
            return new ResponseEntity<String>("Invalid data is provided", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<DailyOperationTrack>(dailyOperationTrackService.updateTodayOperationTrack(dailyOperationTrack, id), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<DailyOperationTrack> viewAllOperations() {
        return dailyOperationTrackService.dailyOperationTracks();
    }


    @GetMapping("/closedailyoperation")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public String closeDailyOperation() throws Exception {
        return dailyOperationTrackService.closeTodayOperationTrack();
    }

}
