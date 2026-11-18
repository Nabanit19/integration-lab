package com.example.demoTest.service;

import com.example.demoTest.entity.Product;
import com.example.demoTest.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void ableToGetProduct(){
        Product dummyProduct =
                new Product(1L,"Desktop",700000,20);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(dummyProduct));

        Product product=productService.getProductById(1L);

        assertEquals(1L,product.getId());
        assertEquals("Desktop",product.getName());
        assertEquals(700000,product.getPrice());
        assertEquals(20,product.getStock());

        verify(productRepository)
                .findById(1L);
    }

    @Test
    void ableToThrowProperExceptionIfProductNotFound(){

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                ()->productService.getProductById(99L));

        assertEquals("Product not found: 99",exception.getMessage());

        verify(productRepository)
                .findById(99L);
    }

    @Test
    void createNewProductIfItDoesntExists(){

        Product dummyProduct =
                new Product(null,"Mobile",20000,30);

        Product savedProduct =
                new Product(1L,"Mobile",20000,30);

        when(productRepository.existsByName("Mobile"))
                .thenReturn(false);

        when(productRepository.save(dummyProduct))
                .thenReturn(savedProduct);

        Product product = productService.createProduct(dummyProduct);

        assertEquals(1L,product.getId());
        assertEquals("Mobile",product.getName());
        assertEquals(20000,product.getPrice());
        assertEquals(30,product.getStock());

        verify(productRepository)
                .existsByName("Mobile");

        verify(productRepository)
                .save(dummyProduct);
    }

    @Test
    void canHandleProperExceptionIfProductAlreadyExists(){

        Product dummyProduct =
                new Product(null,"Mobile",20000,30);

        when(productRepository.existsByName("Mobile"))
                .thenReturn(true);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                ()->productService.createProduct(dummyProduct));

        assertEquals("Product already exists",exception.getMessage());

        verify(productRepository)
                .existsByName("Mobile");

        verify(productRepository,never())
                .save(any(Product.class));

    }
}
