package rw.imps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.imps.domain.Printing;
import rw.imps.domain.ServicesToProvide;
import rw.imps.repository.ServiceToProvideRepo;
import rw.imps.service.ServiceToProvideService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceToProvideServiceImpl implements ServiceToProvideService {

    @Autowired
    private ServiceToProvideRepo serviceToProvideRepo;

    @Override
    public ServicesToProvide createService(ServicesToProvide servicesToProvide) {

        ServicesToProvide servicesToProvide1 = serviceToProvideRepo.save(servicesToProvide);
        return servicesToProvide1;
    }

    @Override
    public ServicesToProvide updateService(ServicesToProvide servicesToProvide, Long serviceToChangeId) throws Exception {
        try {
            Optional<ServicesToProvide> servicesToProvide1 = serviceToProvideRepo.findById(serviceToChangeId);
            if (servicesToProvide1.get() == null) {
                throw new Exception("Service Not found");
            }
            ServicesToProvide updating = servicesToProvide1.get();
            updating = (ServicesToProvide) servicesToProvide.clone();
            return serviceToProvideRepo.save(updating);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public List<ServicesToProvide> viewServices() {
        return serviceToProvideRepo.findAll();
    }

    @Override
    public ServicesToProvide getServiceById(Long id) {
        Optional<ServicesToProvide> servicesToProvide = serviceToProvideRepo.findById(id);
        return servicesToProvide.get();
    }

    @Override
    public ServicesToProvide getServiceByName(String name) {
        return serviceToProvideRepo.findByServiceName(name);
    }
}
