package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.ServicesToProvide;

@Repository
public interface ServiceToProvideRepo extends JpaRepository<ServicesToProvide, Long> {

    public ServicesToProvide findByServiceName(String name);
}
