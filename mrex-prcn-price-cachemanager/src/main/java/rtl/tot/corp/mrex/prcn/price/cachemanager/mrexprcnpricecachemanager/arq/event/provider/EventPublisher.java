package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.provider;

import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.Event;

@FunctionalInterface
public interface EventPublisher {
    boolean publish(Event event);
}
