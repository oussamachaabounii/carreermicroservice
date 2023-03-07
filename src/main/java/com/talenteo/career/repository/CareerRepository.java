package com.talenteo.career.repository;

import com.talenteo.career.model.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    Optional<Career> findByHr(Long id);

    Optional<Career> findById(Integer id);

    public List<Career> findByHrIn(List<Long> ids);

    public List<Career> findByCm(Long id);

    public List<Career> findByCma(Long id);

}
