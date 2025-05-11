import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZapasTable extends AbstractTableModel {
    private List<VysledekZapasu> data = new ArrayList<>();

    public ZapasTable(List<VysledekZapasu> seznamVysledkuB) {
        this.data.addAll(seznamVysledkuB);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VysledekZapasu vysledekZapasuNaRadku = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex+1;
            case 1: return vysledekZapasuNaRadku.getDatum().toString();
            case 2: return vysledekZapasuNaRadku.getHostujiciTym();
            case 3: return vysledekZapasuNaRadku.getVysledekDomaci();
            case 4: return vysledekZapasuNaRadku.getVysledekHoste();
            case 5: return vysledekZapasuNaRadku.isProdlouzeni();
            default: throw new RuntimeException("Špatné číslo sloupce: "+columnIndex+"!");
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Pořadí";
            case 1 -> "Datum";
            case 2 -> "Hostující tým";
            case 3 -> "Výsledek domácí";
            case 4 -> "Výsledek Hosté";
            case 5 -> "Prodloužení";
            default -> throw new RuntimeException("Špatné číslo sloupce: "+column+"!");
        };
    }
}
