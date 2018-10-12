package es.udc.fic.decisionsystem.repository.voto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.voto.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>{

}
