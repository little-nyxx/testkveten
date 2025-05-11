import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZapasOkno extends JFrame {
    private List<VysledekZapasu> seznamVysledku = new ArrayList<>();

    private JLabel datumLabel;
    private JTextField tFDatum;
    private JLabel typLabel;
    private JTextField tFTym;
    private JLabel domaciLabel;
    private JTextField tFDomaci;
    private JTextField tFHoste;
    private JLabel hosteLabel;
    private JCheckBox cbProdlouzeni;
    private JButton btnPredesly;
    private JButton btnDalsi;
    private JPanel pnMain;
    private JTable tableVysledky;
    private JScrollPane scrollPane;

    private int aktualniIndex = 0;



    ZapasTable model = new ZapasTable(seznamVysledku);

    public ZapasOkno() {
        initComponent();
    }

    public void initComponent() {
        setContentPane(pnMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Záznamy o zápasech");
        setSize(800, 400);

        seznamVysledku.add(new VysledekZapasu(LocalDate.of(2025, 5, 11), "Sigma Olomouc", 7, 4, false));
        seznamVysledku.add(new VysledekZapasu(LocalDate.of(2025, 4, 30),"Slovácko", 3, 3, true));

        btnPredesly.addActionListener(e -> predesly());
        btnDalsi.addActionListener(e -> dalsi());

        initMenu();

        //tableVysledky.setModel(model);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuSoubor = new JMenu("Soubor");
        menuBar.add(menuSoubor);
        JMenu menuAkce = new JMenu("Akce");
        menuBar.add(menuAkce);

        JMenuItem miNacti = new JMenuItem("Načti");
        miNacti.addActionListener(e -> {
            try {
                otevri();
            } catch (ZapasException ex) {
                throw new RuntimeException(ex);
            }
        });
        menuSoubor.add(miNacti);

        JMenuItem miReset = new JMenuItem("Resetuj výsledek");
        miReset.addActionListener(e -> reset());
        menuAkce.add(miReset);
        JMenuItem miBilance = new JMenuItem("Zobraz celkovou bilanci");
        miBilance.addActionListener(e -> bilance());
        menuAkce.add(miBilance);
        JMenuItem miTabulka = new JMenuItem("tabulka");
        miTabulka.addActionListener(e -> tabulka());
        menuAkce.add(miTabulka);

    }

    private void tabulka() {
        tableVysledky.setModel(model);
    }



    private void bilance() {
        int vysledekDomaci = 0;
        for (VysledekZapasu vysledekZapasu : seznamVysledku) {
            vysledekDomaci = + seznamVysledku.get(aktualniIndex).getVysledekDomaci();

        }

        int vysledekHoste = 0;
        for (VysledekZapasu vysledekZapasu : seznamVysledku) {
            vysledekHoste = + seznamVysledku.get(aktualniIndex).getVysledekHoste();

        }

        JOptionPane.showMessageDialog(this, vysledekDomaci+":"+vysledekHoste);
    }

    private void reset() {
        tFHoste.setText("0");
        tFDomaci.setText("0");
        cbProdlouzeni.setSelected(false);
    }

    private void otevri() throws ZapasException {
        try {
            JFileChooser fc = new JFileChooser();

            int result = fc.showOpenDialog(this);

            if(result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                nactiSoubor(String.valueOf(selectedFile));
            }
        } catch (ZapasException e) {
            throw new ZapasException("Chyba při výběru souboru!");
        }
    }

    public void nactiSoubor(String nazevSouboru) throws ZapasException {
        //int aktualniIndex = 0;
        String oddelovac = ";";
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(nazevSouboru)))){
            while (sc.hasNextLine()) {
                String radek = sc.nextLine();
                pridejDoSeznamu(parseZapas(radek, oddelovac));
            }
            if(seznamVysledku != null) {
                nactiDoOkna();
            } else throw new ZapasException("Seznam zápasů je prázdný!");
        } catch (FileNotFoundException e) {
            throw new ZapasException("Soubor "+nazevSouboru+ " nebyl nalezen!");
        }
    }

    private VysledekZapasu parseZapas(String radek, String oddelovac) throws ZapasException {
        String[] polozky = radek.split(oddelovac);
        if(polozky.length != 5) {
            throw new ZapasException("Špatný počet položek v seznamu!");
        }
        LocalDate datum = LocalDate.parse(polozky[3].trim());
        String tym = polozky[0].trim();
        int domaci = Integer.parseInt(polozky[1].trim());
        int hoste = Integer.parseInt(polozky[2].trim());
        boolean prodlouzeni = Boolean.parseBoolean(polozky[4].trim());

        return new VysledekZapasu(datum, tym, domaci, hoste, prodlouzeni);
    }

    private void pridejDoSeznamu(VysledekZapasu vysledek) {
        seznamVysledku.add(vysledek);
    }

    private void predesly() {
        if(aktualniIndex == 0) {
            JOptionPane.showMessageDialog(this,"Nejsou k dispozici další záznamy!");
        } else {
            aktualniIndex--;
            nactiDoOkna();
        }
    }

    private void dalsi() {
        if(aktualniIndex == seznamVysledku.size()-1) {
            JOptionPane.showMessageDialog(this, "Nejsou k dispozici další záznamy!");
        } else {
            aktualniIndex++;
            nactiDoOkna();
        }
    }

    public void nactiDoOkna() {
        String datum = seznamVysledku.get(aktualniIndex).getDatum().toString();
        tFDatum.setText(datum);
        tFTym.setText(seznamVysledku.get(aktualniIndex).getHostujiciTym());
        String vysDomaci = Integer.toString(seznamVysledku.get(aktualniIndex).getVysledekDomaci());
        tFDomaci.setText(vysDomaci);
        String vysHoste = Integer.toString(seznamVysledku.get(aktualniIndex).getVysledekHoste());
        tFHoste.setText(vysHoste);
        cbProdlouzeni.setSelected(seznamVysledku.get(aktualniIndex).isProdlouzeni());

        ZapasTable model = new ZapasTable(seznamVysledku);
        //tableVysledky.setModel(model);

    }

    public void vyhlasChybu (ZapasException e) {
        JOptionPane.showMessageDialog(this, e);
    }



}
