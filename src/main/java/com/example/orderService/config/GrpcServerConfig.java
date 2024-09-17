package com.example.orderService.config;

import com.example.orderService.service.ItemServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PreDestroy;

@Configuration
public class GrpcServerConfig {

    @Value("${grpc.server.port:9093}") // Port can be configured in application.properties
    private int grpcServerPort;

    private Server server;

    @Bean
    public Server grpcServer(ItemServiceImpl itemServiceImpl) {
        server = ServerBuilder.forPort(grpcServerPort)
                .addService(itemServiceImpl)
                .build();

        try {
            server.start();
            System.out.println("gRPC server started on port " + grpcServerPort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server;
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
            System.out.println("gRPC server stopped");
        }
    }
}
