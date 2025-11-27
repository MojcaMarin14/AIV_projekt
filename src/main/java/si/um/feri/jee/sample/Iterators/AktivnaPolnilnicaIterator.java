package si.um.feri.jee.sample.Iterators;


import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.qualifiers.PolnilnicaList;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AktivnaPolnilnicaIterator implements Iterator<Polnilnica> {
    private final List<Polnilnica> stations;
    private int position = 0;

    public AktivnaPolnilnicaIterator(List<Polnilnica> stations) {
        this.stations = stations;
    }

    @Override
    public boolean hasNext() {
        while (position < stations.size()) {
            if (stations.get(position).isAvailable()) {
                return true;
            }
            position++; // preskoči neaktivne
        }
        return false;
    }

    @Override
    public Polnilnica next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Ni več aktivnih polnilnic.");
        }
        return stations.get(position++);
    }
}
