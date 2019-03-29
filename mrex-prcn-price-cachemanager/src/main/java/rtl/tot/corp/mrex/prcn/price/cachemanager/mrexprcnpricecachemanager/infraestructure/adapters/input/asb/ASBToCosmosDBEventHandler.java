package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.input.asb;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.Event;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.EventHandler;

import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.events.EventType;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.events.PriceCreateNotifiedEvent;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.events.PriceUpdatedNotifiedEvent;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.model.Price;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.model.Price.Tax;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.model.PriceRepository;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.output.asb.EventPublisherService;


import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ASBToCosmosDBEventHandler implements EventHandler {

	@Autowired
	EventPublisherService publisher;
	private final PriceRepository priceRepository;
	// private final RestTemplate restTemplate;
	// private final String productsApiUrl;

	public ASBToCosmosDBEventHandler(// final RestTemplate restTemplate,
			final PriceRepository priceRepository
	// ,@Value("${api.products.endpoint}") final String productsApiUrl
	) {
		// this.priceApiUrl = priceApiUrl;
		this.priceRepository = priceRepository;
		// this.restTemplate = restTemplate;
	}

	@Override
	public void processEvent(Event event) {


      ObjectMapper mapper = new ObjectMapper();

		Price price;
		try {
			price = mapper.readValue(event.getMetadata(), Price.class);
			
		    if (event.getEventType().equals(EventType.PRICE_CREATED.toString())) {
		    	
		    log.info("Persisting price " + price);
			priceRepository.save(price);
				
			PriceCreateNotifiedEvent integrationEvent = new PriceCreateNotifiedEvent();
		        
		    integrationEvent.setSku(price.getSku());
		    integrationEvent.setCurrentPrice(price.getCurrentPrice());
		    integrationEvent.setRegularPrice(price.getRegularPrice());
		    integrationEvent.setStore(price.getStore());
   
		    
		    	publisher.publish(EventType.PRICE_CREATED, integrationEvent);
				log.info("Published Price Created" + event.getMetadata());
				
			}
			if (event.getEventType().equals(EventType.PRICE_UPDATED.toString())) {
				
				
					log.info("Updating price " + price);
					
					Optional<Price> priceObject = priceRepository.findById(event.getEntityId());
				
				
				
					if (priceObject.isPresent()) {
			
						Price priceFromDB = priceObject.get();
						priceFromDB.setCurrentPrice(price.getCurrentPrice());
						priceFromDB.setRegularPrice(price.getRegularPrice());
						priceFromDB.setStore(price.getStore());
						priceFromDB.setPromotionPrice(price.getPromotionPrice());
						if (price.getDetraction() != null) {
						priceFromDB.getDetraction().setCodeDetraction(price.getDetraction().getCodeDetraction());
						priceFromDB.getDetraction().setNameDetraction(price.getDetraction().getNameDetraction());
						priceFromDB.getDetraction().setPercentDetraction(price.getDetraction().getPercentDetraction());
						}
		    			for(Tax tax: price.getTaxes()) {
		    				Price.Tax newTax = new Price.Tax();
		    				newTax.setAmountTax(tax.getAmountTax());
		    				newTax.setTax(tax.getTax());
		    				priceFromDB.getTaxes().add(newTax);
		    			}
						
						priceRepository.save(priceFromDB);
					
					
						PriceUpdatedNotifiedEvent integrationEvent = new PriceUpdatedNotifiedEvent();
			        
						integrationEvent.setSku(price.getSku());
					    integrationEvent.setCurrentPrice(price.getCurrentPrice());
					    integrationEvent.setRegularPrice(price.getRegularPrice());
					    integrationEvent.setStore(price.getStore());
			   	    
					    publisher.publish(EventType.PRICE_UPDATED, integrationEvent);
					    log.info("Published Price updated" + event.getMetadata());
					}
					else {
						 log.info("Price not found to update" + event.getMetadata());
						}
				
			}
		    
		}
		catch(JsonParseException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(JsonMappingException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		}catch(IOException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
		}catch(Exception e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	// // Always evict cache first
	// try {
	// // This call needs to be jailed because the CosmoDB throws an exception
	// // when the target resource doesn't exist.
	// // productsRepository.deleteById(event.getEntityId());
	// } catch (final Exception ex) {
	// log.warn("Resource to be deleted does not exist: " + ex.getMessage());
	// }
	//
	// if (!event.getEventType().equals(EventType.PRODUCT_DELETED.toString())) {
	// final String url = productsApiUrl + "/" + event.getEntityId();
	//
	// // When an event arrives, ask the Core API for full domain information
	// // And for demonstration purposes, lets assume that the event payload
	// // doesn't have all the information we actually need.
	// // final Product product = restTemplate.getForObject(url, Product.class);
	// // if (product != null ) {
	//
	// ObjectMapper mapper = new ObjectMapper();
	//
	// Product product;
	// try {
	// product = mapper.readValue(event.getMetadata(), Product.class);
	// log.info("Persisting product " + product);
	// productRepository.save(product);
	//
	// } catch (JsonParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
}

}
