package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.input.asb;

import org.springframework.stereotype.Service;

import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.provider.EventSubscriber;



@Service
public class EventSubscriberService {

    public EventSubscriberService(final EventSubscriber eventSubscriber,
                                  final ASBToCosmosDBEventHandler asbToCosmosDBEventHandler) {
        eventSubscriber.registerEventHandler(asbToCosmosDBEventHandler);
    }

}
