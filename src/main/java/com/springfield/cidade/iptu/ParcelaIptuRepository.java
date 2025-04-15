package com.springfield.cidade.iptu;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParcelaIptuRepository extends JpaRepository<ParcelaIptu, Integer> {
    List<ParcelaIptu> findByCidadaoId(Integer cidadaoId);
}
