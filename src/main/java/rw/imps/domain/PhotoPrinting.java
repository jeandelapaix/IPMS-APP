package rw.imps.domain;

import rw.imps.domain.enums.PhotoPrintingType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Photo Printing")
public class PhotoPrinting  extends  Printing{

    private PhotoPrintingType printingType;
    private String printingSize;

    public PhotoPrintingType getPrintingType() {
        return printingType;
    }

    public void setPrintingType(PhotoPrintingType printingType) {
        this.printingType = printingType;
    }

    public String getPrintingSize() {
        return printingSize;
    }

    public void setPrintingSize(String printingSize) {
        this.printingSize = printingSize;
    }
}
