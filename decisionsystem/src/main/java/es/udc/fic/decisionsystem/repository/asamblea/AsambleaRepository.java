package es.udc.fic.decisionsystem.repository.asamblea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;

@Repository
public interface AsambleaRepository extends JpaRepository<Asamblea, Integer> {

}
