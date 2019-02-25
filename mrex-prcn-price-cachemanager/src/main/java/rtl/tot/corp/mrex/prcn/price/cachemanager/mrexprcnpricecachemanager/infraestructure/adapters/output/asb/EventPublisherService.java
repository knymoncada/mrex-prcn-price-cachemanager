package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.output.asb;
import lombok.extern.slf4j.Slf4j;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.Event;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.EventBuilder;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.event.provider.EventPublisher;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.arq.infra.exception.InvalidParameterException;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.domain.events.EventType;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.input.asb.EventDomain;
import rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager.infraestructure.adapters.input.asb.EventProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.time.LocalDateTime;

@Slf4j
@Component
public class EventPublisherService {

    private final EventPublisher eventPublisher;
    private final EventProperties eventProperties;

    @Autowired
    public EventPublisherService(final EventPublisher eventPublisher,
                                 final EventProperties eventProperties) {
        this.eventPublisher = eventPublisher;
        this.eventProperties = eventProperties;
    }

    public boolean publish(final EventType eventType, final EventDomain eventDomain) {
        boolean eventSent = false;
        try {
            final Event event = EventBuilder.newBuilder()
                    .generateEventId()
                    .eventType(eventType.toString())
                    .entityId(eventDomain.getEntityId())
                    .entityType(eventDomain.getEntityType())
                    .dateTime(LocalDateTime.now())
                    .version(eventProperties.getVersion())
                    .country(eventProperties.getCountry())
                    .commerce(eventProperties.getCommerce())
                    .channel(eventProperties.getChannel())
                    .domain("")
                    .capability("")
                    .mimeType(eventProperties.getMimeType())
                    .metadata(eventDomain.getMetadata())
                    .build();

            eventSent = eventPublisher.publish(event);
            log.info("Event published: " + event.getMetadata());
        } catch (InvalidParameterException e) {
            log.error(eventType.toString() + " event could not be send. Cause: " + e.getMessage());
        }
        return eventSent;
    }

}
