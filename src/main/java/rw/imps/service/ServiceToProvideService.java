package rw.imps.service;

import rw.imps.domain.ServicesToProvide;

import java.util.List;

public interface ServiceToProvideService{

    public ServicesToProvide createService(ServicesToProvide servicesToProvide);
    public ServicesToProvide updateService(ServicesToProvide servicesToProvide,Long serviceToChangeId) throws Exception;
    public List<ServicesToProvide> viewServices();
    public ServicesToProvide getServiceById(Long id);
    public ServicesToProvide getServiceByName(String name);

}
