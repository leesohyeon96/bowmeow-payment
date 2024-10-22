package com.bowmeow.payment.service;

import com.bowmeow.bowmeow_payment.PaymentServiceProto.CreateOrderResponse;
import com.bowmeow.payment.PaymentServiceGrpc;
import com.bowmeow.payment.PaymentServiceProto;
import com.bowmeow.payment.client.ProductClient;
import com.bowmeow.payment.entity.Order;
import com.bowmeow.payment.util.OrderStatus;
import com.bowmeow.product.ProductServiceProto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Import 를 이용한 결제 Service
 * @author shl
 * @since 2024-07-14
 */
@Service
@RequiredArgsConstructor
public abstract class ImportPaymentGrpcServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    private ProductClient productClient;
    private final PaymentJPAService paymentJPAService;

    /**
     * Product 서비스에서 상품 정보 가져오기
     * @param productId 가져올 상품 일련번호
     * @return {@link com.bowmeow.product.ProductServiceProto.ProductInfo}
     */
    public ProductServiceProto.ProductInfo getProductInfo(Integer productId) {
        return productClient.getProductInfo(productId);
    }

    /**
     * 주문 생성 및 주문서 페이지로 redirect
     * @param request {@link com.bowmeow.payment.PaymentServiceProto.ProductInfoForOrder}
     */
    public CreateOrderResponse createOrder(PaymentServiceProto.ProductInfoForOrder request) {
        // 1. 주문 생성
        Order order = new Order();
        order.setOrderStatus(OrderStatus.WAIT.name());
        order.setProductPurchaseCount(request.getProductPurchaseCount());
        // TODO : 사용자 일련번호도 필요한데 이건 그 JWTUtils 에서 가져오는게 맞나 아니면 ProductInfoForOrder에서 가져오는게 맞나
        // order.setUserId();
        Long orderId = paymentJPAService.saveOrder(order).getOrderId();

        // 2. 성공후 주문서 페이지로 redirect >> "/payment/success?orderId=" + order.getId(); // 주문 ID 포함
        String redirectUrl = "";
        if (orderId != null) {
            redirectUrl = "/payment/success?orderId=" + orderId;
        }
        // TODO : product 로 반환해서 redirect 할건지, 아니면 그냥 여기서 할건지?
        return CreateOrderResponse.newBuilder()
                .setRedirectUrl(redirectUrl)
                .build();
    }
}
