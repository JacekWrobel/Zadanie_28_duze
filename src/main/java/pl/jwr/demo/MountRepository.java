package pl.jwr.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MountRepository extends JpaRepository<Mount, Long> {

    @Query(value = "SELECT * FROM mount ORDER BY name ASC", nativeQuery = true)
    List<Mount> findByOrderByNameAsc();

    @Query("SELECT m FROM Mount m ORDER BY altitude DESC")
    List<Mount> findAllByOrderByAltitudeDesc();

    @Query(value = "SELECT * FROM mount ORDER BY range_id ASC", nativeQuery = true)
    List<Mount> findByOrderByRangeAsc();

    @Query(value = "SELECT * FROM mount ORDER BY likes DESC", nativeQuery = true)
    List<Mount> findByOrderByLikesDesc();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mount WHERE range_id=?1", nativeQuery = true)
    void deleteByRangeId(Long id);

}


