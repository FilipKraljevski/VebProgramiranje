package mk.ukim.finki.av1wp.repository.jpa;

import mk.ukim.finki.av1wp.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
