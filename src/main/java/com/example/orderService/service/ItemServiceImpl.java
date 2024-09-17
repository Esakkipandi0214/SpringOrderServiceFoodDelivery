package com.example.orderService.service;

import com.example.RestController.ItemRequest;
import com.example.RestController.ItemResponse;
import com.example.RestController.ItemServiceGrpc;
import com.example.orderService.model.Product;
import com.example.orderService.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ItemServiceGrpc.ItemServiceImplBase {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void receiveItem(ItemRequest request, StreamObserver<ItemResponse> responseObserver) {
        String name = request.getName();
        double price = request.getPrice();
        String message;
        long productId = 0;

        if (!name.isEmpty() && price > 0) {
            try {
                // Create and save the product
                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                Product savedProduct = productRepository.save(product);

                // Get the ID of the saved product
                productId = savedProduct.getId();

                message = "Product created successfully";
            } catch (Exception e) {
                // Handle any exceptions that occur during saving
                message = "An error occurred: " + e.getMessage();
            }
        } else {
            message = "Invalid product data";
        }

        // Build the response
        ItemResponse response = ItemResponse.newBuilder()
                .setMessage(message)
                .setId(productId) // Set the product ID
                .build();

        // Send the response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
