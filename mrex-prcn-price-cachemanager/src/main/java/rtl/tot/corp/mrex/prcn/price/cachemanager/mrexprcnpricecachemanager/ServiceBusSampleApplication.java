package rtl.tot.corp.mrex.prcn.price.cachemanager.mrexprcnpricecachemanager;
import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ServiceBusSampleApplication {

    private SubscriptionClient subscriptionClient;

    public static void main(String[] args) {
    	ServiceBusSampleApplication app = new ServiceBusSampleApplication();
    	try {
			app.run();
		} catch (ServiceBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void run() throws ServiceBusException, InterruptedException {
        
    	 final String subscription = new StringBuffer("tot-corp-orion-pricechanges-topic-mid")
                 .append("/subscriptions/")
                 .append("TOT-CORP-ORION-PriceChanges-Subs-Topic-MID")
                 .toString();
         subscriptionClient =
                 new SubscriptionClient(new ConnectionStringBuilder("Endpoint=sb://tot-corp-orion-int-qa.servicebus.windows.net/;SharedAccessKeyName=totorionadm;SharedAccessKey=+uHMYKcSeBfapKNgfqLkXuQe8fgpz2GR4GGKkChZB4s=;EntityPath=tot-corp-orion-pricechanges-topic-mid", 
                		 subscription), ReceiveMode.PEEKLOCK);
     
        receiveSubscriptionMessage();
    }


    private void receiveSubscriptionMessage() throws ServiceBusException, InterruptedException {
        subscriptionClient.registerMessageHandler(new MessageHandler(), new MessageHandlerOptions());

        TimeUnit.SECONDS.sleep(5);
    }

    static class MessageHandler implements IMessageHandler {
        public CompletableFuture<Void> onMessageAsync(IMessage message) {
            final String messageString = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received message: " + messageString);
            return CompletableFuture.completedFuture(null);
        }

        public void notifyException(Throwable exception, ExceptionPhase phase) {
            System.out.println(phase + " encountered exception:" + exception.getMessage());
        }
    }
}