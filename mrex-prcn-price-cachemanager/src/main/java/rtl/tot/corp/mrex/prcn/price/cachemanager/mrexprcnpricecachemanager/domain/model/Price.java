package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.model;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.Data;
 
@Data
public class Price {
	@Id
	@NotNull
	String sku;	
	@NotNull
	Long store;
	@NotNull
	Double currentPrice;	
	@NotNull
	Double regularPrice;
}

