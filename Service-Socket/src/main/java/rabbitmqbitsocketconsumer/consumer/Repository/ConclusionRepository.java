package rabbitmqbitsocketconsumer.consumer.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rabbitmqbitsocketconsumer.consumer.Entity.ConclusionEntity;

@Repository
public interface ConclusionRepository extends JpaRepository<ConclusionEntity, String> {
}
