package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.provider;

import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.EventHandler;

@FunctionalInterface
public interface EventSubscriber {
    boolean registerEventHandler(EventHandler eventHandler);
}
