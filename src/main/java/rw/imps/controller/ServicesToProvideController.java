package rw.imps.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.OAuth2Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.imps.domain.*;
import rw.imps.service.ServiceToProvideService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/servicetoprovide")
@CrossOrigin
public class ServicesToProvideController {

    @Autowired
    private ServiceToProvideService providingService;

    @PostMapping("/paperprinting")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> registerPaperPrinting(@RequestBody @Valid PaperPrinting servicesToProvide, BindingResult result) {
        return getResponseEntity(servicesToProvide, result);
    }

    @PostMapping("/imageprinting")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})

    public ResponseEntity<?> registerPhotoPrinting(@RequestBody @Valid PhotoPrinting servicesToProvide, BindingResult result) {
        return getResponseEntity(servicesToProvide, result);
    }


    @PostMapping("/photogrid")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> registerPhotoGridForSale(@RequestBody @Valid PhotoGridForSale servicesToProvide, BindingResult result) {
        return getResponseEntity(servicesToProvide, result);
    }

    @PostMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> registerDefaultService(@RequestBody @Valid ServicesToProvide servicesToProvide, BindingResult result) {
        return getResponseEntity(servicesToProvide, result);
    }

    private ResponseEntity<?> getResponseEntity(ServicesToProvide servicesToProvide, BindingResult result) {
        try {
            if (!result.hasErrors()) {
                return new ResponseEntity<ServicesToProvide>(providingService.createService(servicesToProvide), HttpStatus.OK);
            }
            return new ResponseEntity<String>("Invalid data is provided, unable to proceed", HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            return new ResponseEntity<String>("Something is wrong: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<ServicesToProvide> servicesToProvideList(){
        return providingService.viewServices();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ServicesToProvide getOneServiceToProvide(@PathVariable Long id){
        return providingService.getServiceById(id);
    }

}
