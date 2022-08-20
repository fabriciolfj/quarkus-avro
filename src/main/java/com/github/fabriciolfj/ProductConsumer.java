package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;
import org.acme.kafka.quarkus.Product;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/consumer")
@ApplicationScoped
public class ProductConsumer {

    @Channel("products-from-kafka")
    Multi<Product> products;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.TEXT_PLAIN)
    public Multi<String> stream() {
        return products.map(product -> String.format("'%s'from %s", product.getId(), product.getDescribe()));
    }
}
