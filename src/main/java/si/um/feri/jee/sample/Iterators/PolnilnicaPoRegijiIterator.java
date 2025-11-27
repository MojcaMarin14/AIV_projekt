package si.um.feri.jee.sample.Iterators;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.qualifiers.DefaultPonudnik;
import si.um.feri.jee.sample.qualifiers.PolnilnicaList;
import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.Ponudnik;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class PolnilnicaPoRegijiIterator implements Iterator<Polnilnica> {

    private List<Polnilnica> polnilnice;
    private String regija;

    private Ponudnik ponudnik;
    private int trenutniIndex = 0;


    public PolnilnicaPoRegijiIterator(List<Polnilnica> polnilnice, String regija, Ponudnik ponudnik) {
        this.polnilnice = polnilnice;
        this.regija = regija != null ? regija.trim() : "";
        this.ponudnik = ponudnik;
    }

    @Override
    public boolean hasNext() {
        while (trenutniIndex < polnilnice.size()) {
            Polnilnica trenutnaPolnilnica = polnilnice.get(trenutniIndex);
            if (trenutnaPolnilnica.getRegija() != null &&
                    trenutnaPolnilnica.getRegija().equalsIgnoreCase(regija) &&
                    ponudnik != null &&
                    ponudnik.equals(trenutnaPolnilnica.getPonudnik())) {
                return true;
            }
            trenutniIndex++;
        }
        return false;
    }

    @Override
    public Polnilnica next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Ni več polnilnic v regiji '" + regija + "' pri ponudniku '" + ponudnik + "'.");
        }
        return polnilnice.get(trenutniIndex++);
    }
}
