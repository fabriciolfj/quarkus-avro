package com.github.fabriciolfj;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.acme.kafka.quarkus.Product;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

    @Channel("products")
    Emitter<Product> producer;

    @POST
    public Response enqueue(final ProductDTO dto) {
        LOGGER.info("Sending product {} to kafka", dto);
        var product = Product.newBuilder()
                .setDescribe(dto.getDescribe())
                .setId(dto.getId())
                .build();

        producer.send(product);
        return Response.accepted().build();
    }

}
