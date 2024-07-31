package com.bowmeow.payment.config;

import com.bowmeow.payment.service.ImportPaymentServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcConfig {

    @Bean
    public Server grpcServer() throws IOException {
        Server server = ServerBuilder.forPort(9091)
                .addService((BindableService) new ImportPaymentServiceImpl()) // Add the service implementation
                .build()
                .start();

        // Add a shutdown hook to properly close the server on JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            System.err.println("Server shut down");
        }));

        return server;
    }

}
