package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.provider;

import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.Event;

public interface EventConverter<T> {
    T toMessage(Event event);
    Event fromMessage(T message);
}
