package rw.imps.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@DiscriminatorValue("Photo Grid For Sale")
public class PhotoGridForSale extends ServicesToProvide {

    @NotEmpty(message = "Grid Size Can not be null")
    private String gridSize;

    public String getGridSize() {
        return gridSize;
    }

    public void setGridSize(String gridSize) {
        this.gridSize = gridSize;
    }

}
