package rw.imps.domain;


import rw.imps.domain.enums.ServiceCategory;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "service_type")
public class ServicesToProvide implements Cloneable{
    @Id
    @GeneratedValue
    private Long id;
    private String serviceName;
    private double minimumPrice;
    private ServiceCategory serviceCategory;
    private double percentageForAgent;
    private String description;
    @Transient
    private ServiceToProvideType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public double getPercentageForAgent() {
        return percentageForAgent;
    }

    public void setPercentageForAgent(double percentageForAgent) {
        this.percentageForAgent = percentageForAgent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }

    public ServiceToProvideType getType() {
        return type;
    }

    public void setType(ServiceToProvideType type) {
        this.type = type;
    }
}
