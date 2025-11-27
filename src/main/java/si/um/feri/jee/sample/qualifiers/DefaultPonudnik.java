package si.um.feri.jee.sample.qualifiers;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier  // Marks this as a CDI qualifier
@Retention(RetentionPolicy.RUNTIME)  // Available at runtime
public @interface DefaultPonudnik {
}