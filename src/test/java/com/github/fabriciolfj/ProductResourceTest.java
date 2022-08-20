package com.github.fabriciolfj;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.awaitility.Awaitility.await;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class ProductResourceTest {

    @TestHTTPResource("/consumer")
    URI consumedProducts;

    @Test
    public void testHelloEndpoint() throws InterruptedException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(consumedProducts);

        List<String> received = new CopyOnWriteArrayList<>();

        SseEventSource source = SseEventSource.target(target).build();
        source.register(inboundSseEvent -> received.add(inboundSseEvent.readData()));

        ExecutorService movieSender = sendindProducts();

        source.open();

        await().atMost(5, SECONDS).until(() -> received.size() >= 1);
        source.close();

        movieSender.shutdownNow();
        movieSender.awaitTermination(5, SECONDS);
    }

    private ExecutorService sendindProducts() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            while (true) {
                given()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "    \"id\" : \"1\",\n" +
                                "    \"describe\" : \"arroz\"\n" +
                                "}")
                        .when()
                        .post("/products")
                        .then()
                        .statusCode(202);

                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        return executorService;
    }
}
