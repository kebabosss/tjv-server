package fit.bitjv.semestral.domain;

import java.io.Serializable;

public interface EntityWithID<T> extends Serializable {
    T getId();
}
