package com.charter.enterprise.motd.repository;

import com.charter.enterprise.motd.Motd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotdRepository extends JpaRepository<Motd, Integer> {
}
