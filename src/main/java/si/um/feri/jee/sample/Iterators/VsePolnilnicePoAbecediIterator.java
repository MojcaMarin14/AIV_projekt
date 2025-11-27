package si.um.feri.jee.sample.Iterators;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.Ponudnik;

import java.util.*;

public class VsePolnilnicePoAbecediIterator implements Iterator<Polnilnica> {

    private final List<Polnilnica> polnilnice;
    private int trenutniIndex = 0;


    public VsePolnilnicePoAbecediIterator(List<Ponudnik> ponudniki) {
        polnilnice = new ArrayList<>();

        // Združimo vse polnilnice iz vseh ponudnikov
        for (Ponudnik ponudnik : ponudniki) {
            if (ponudnik != null && ponudnik.getPolnilnice() != null) {
                polnilnice.addAll(ponudnik.getPolnilnice());
            }
        }

        // Sortiramo polnilnice po lokaciji (abecedno), upoštevamo možnost `null` vrednosti
        polnilnice.sort(Comparator.comparing(
                p -> p.getLokacija() != null ? p.getLokacija() : "",
                String.CASE_INSENSITIVE_ORDER
        ));
    }

    // Preveri, ali obstaja še naslednja polnilnica
    @Override
    public boolean hasNext() {
        return trenutniIndex < polnilnice.size();
    }

    // Vrne naslednjo polnilnico
    @Override
    public Polnilnica next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Ni več polnilnic.");
        }
        return polnilnice.get(trenutniIndex++);
    }
}
