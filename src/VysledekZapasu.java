import java.time.LocalDate;

public class VysledekZapasu {
    private LocalDate datum;
    private String hostujiciTym;
    private int vysledekDomaci;
    private int vysledekHoste;
    private boolean prodlouzeni;

    public VysledekZapasu(LocalDate datum, String hostujiciTym, int vysledekDomaci, int vysledekHoste, boolean prodlouzeni) {
        this.datum = datum;
        this.hostujiciTym = hostujiciTym;
        this.vysledekDomaci = vysledekDomaci;
        this.vysledekHoste = vysledekHoste;
        this.prodlouzeni = prodlouzeni;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getHostujiciTym() {
        return hostujiciTym;
    }

    public void setHostujiciTym(String hostujiciTym) {
        this.hostujiciTym = hostujiciTym;
    }

    public int getVysledekDomaci() {
        return vysledekDomaci;
    }

    public void setVysledekDomaci(int vysledekDomaci) {
        this.vysledekDomaci = vysledekDomaci;
    }

    public int getVysledekHoste() {
        return vysledekHoste;
    }

    public void setVysledekHoste(int vysledekHoste) {
        this.vysledekHoste = vysledekHoste;
    }

    public boolean isProdlouzeni() {
        return prodlouzeni;
    }

    public void setProdlouzeni(boolean prodlouzeni) {
        this.prodlouzeni = prodlouzeni;
    }
}
