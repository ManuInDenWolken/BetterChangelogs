package net.navrix.betterchangelogs.repository;

import com.google.common.base.Optional;

import java.util.List;

public interface Repository<K, V> {

    void create(V v);

    Optional<V> read(K k);

    void update(V v);

    void delete(V v);

    List<V> findAll();

}
