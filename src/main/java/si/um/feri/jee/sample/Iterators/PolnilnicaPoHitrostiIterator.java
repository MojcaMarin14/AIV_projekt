package si.um.feri.jee.sample.Iterators;


import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.qualifiers.PolnilnicaList;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class PolnilnicaPoHitrostiIterator implements Iterator<Polnilnica> {
    private final List<Polnilnica> polnilnice;
    private final double minHitrost;
    private int position = 0;

    public PolnilnicaPoHitrostiIterator(List<Polnilnica> polnilnice, double minHitrost) {
        this.polnilnice = polnilnice;
        this.minHitrost = minHitrost;
    }

    @Override
    public boolean hasNext() {
        while (position < polnilnice.size()) {
            if (polnilnice.get(position).getHitrostPolnjenja() >= minHitrost) {
                return true;
            }
            position++; // preskoči polnilnice, ki ne ustrezajo kriteriju
        }
        return false;
    }

    @Override
    public Polnilnica next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Ni več polnilnic z minimalno hitrostjo " + minHitrost + " kW.");
        }
        return polnilnice.get(position++);
    }
}
