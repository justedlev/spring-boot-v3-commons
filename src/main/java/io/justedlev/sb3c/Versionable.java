package io.justedlev.sb3c;

public interface Versionable<V> {

    V getVersion();

    void setVersion(V version);

}
