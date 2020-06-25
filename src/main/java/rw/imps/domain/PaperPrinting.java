package rw.imps.domain;

import rw.imps.domain.enums.PaperPrintingType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Paper Printing")
public class PaperPrinting extends Printing {

    private PaperPrintingType printingType;
    private int negotiablePages;

    public PaperPrintingType getPrintingType() {
        return printingType;
    }

    public void setPrintingType(PaperPrintingType printingType) {
        this.printingType = printingType;
    }

    public int getNegotiablePages() {
        return negotiablePages;
    }

    public void setNegotiablePages(int negotiablePages) {
        this.negotiablePages = negotiablePages;
    }
}
