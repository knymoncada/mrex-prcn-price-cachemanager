package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.model;
import java.util.HashSet;
import java.util.Set;

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
	@NotNull
	Double promotionPrice;
	@NotNull
	Set<Tax> taxes = new HashSet<Tax>();
	Detraction detraction = new Detraction();
	@Data
	public 	static class Tax{
		String tax;
		Double amountTax;
	}
	@Data
	public class Detraction {
		String codeDetraction;
		String nameDetraction;
		Double percentDetraction;
	}
}

