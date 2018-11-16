package pl.algorit.restfulservices;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BackendServiceStatusManager {

    public boolean isServiceAvailable(String serviceName) {
        return new Random().nextBoolean();
    }

}
