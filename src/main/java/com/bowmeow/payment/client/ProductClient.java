package com.bowmeow.payment.client;

import com.bowmeow.product.ProductServiceGrpc;
import com.bowmeow.product.ProductServiceProto;
import com.bowmeow.product.ProductServiceProto.ProductRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductClient {

    @Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private int port;

    private ProductServiceGrpc.ProductServiceBlockingStub blockingStub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = ProductServiceGrpc.newBlockingStub(channel);
    }

    /**
     * 결제를 위한 상품 정보 반환
     * @param productSno 상품 정보 일련번호
     * @return 결제를 위한 상품 정보
     */
    public ProductServiceProto.ProductInfo getProductInfo(int productSno) {
        ProductRequest request = ProductRequest.newBuilder().setProductSno(productSno).build();
        return blockingStub.getProductInfo(request);
    }

}
