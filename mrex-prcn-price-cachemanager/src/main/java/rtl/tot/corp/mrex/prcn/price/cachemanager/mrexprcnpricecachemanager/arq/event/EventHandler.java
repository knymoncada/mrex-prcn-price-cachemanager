package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event;

@FunctionalInterface
public interface EventHandler {
    void processEvent(final Event event);
}
