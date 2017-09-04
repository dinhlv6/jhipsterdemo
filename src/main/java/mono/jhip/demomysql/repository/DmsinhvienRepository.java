package mono.jhip.demomysql.repository;

import mono.jhip.demomysql.domain.Dmsinhvien;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dmsinhvien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DmsinhvienRepository extends JpaRepository<Dmsinhvien, Long> {

}
