package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.model;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;

@Repository
@Primary
public interface PriceRepository extends DocumentDbRepository<Price, String> {
 
}
