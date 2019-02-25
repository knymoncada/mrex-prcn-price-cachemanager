package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.events;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.input.asb.EventDomain;


@JsonIgnoreProperties({"mapper", "entityType"})
public class PriceCreateNotifiedEvent implements EventDomain{

			
		private final ObjectMapper mapper = new ObjectMapper();
		 
		
		@Id
		@NotNull
		String sku;	
		@NotNull
		Long store;
		@NotNull
		Double currentPrice;	
		@NotNull
		Double regularPrice;
		
		@Override
		@JsonIgnore
		public String getEntityId() {
			// TODO Auto-generated method stub
			return sku;
		}

		@Override
		@JsonIgnore
		public String getMetadata() {
			String jsonValue;
	        try {
	            jsonValue = mapper.writeValueAsString(this);
	        } catch (JsonProcessingException e) {
	            jsonValue = super.toString();
	        }
	        return jsonValue;
		}

		@Override
		public String getEntityType() {
			return getClass().getName();
		}

		public ObjectMapper getMapper() {
			return mapper;
		}

		public String getSku() {
			return sku;
		}

		public void setSku(String sku) {
			this.sku = sku;
		}

		public Long getStore() {
			return store;
		}

		public void setStore(Long store) {
			this.store = store;
		}

		public Double getCurrentPrice() {
			return currentPrice;
		}

		public void setCurrentPrice(Double currentPrice) {
			this.currentPrice = currentPrice;
		}

		public Double getRegularPrice() {
			return regularPrice;
		}

		public void setRegularPrice(Double regularPrice) {
			this.regularPrice = regularPrice;
		}



}
