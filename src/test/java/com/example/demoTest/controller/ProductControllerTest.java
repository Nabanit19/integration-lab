package com.example.demoTest.controller;

import com.example.demoTest.entity.Product;
import com.example.demoTest.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void ableToGetProperProductResponse() throws Exception {

        Product product =
                new Product(1L,"Bicycle",15000,30);

        when(productService.getProductById(1L))
                .thenReturn(product);

        mockMvc.perform(get("/api/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bicycle"))
                .andExpect(jsonPath("$.price").value(15000))
                .andExpect(jsonPath("$.stock").value(30));

        verify(productService)
                .getProductById(1L);

    }

    @Test
    void ableToGetProperExceptionAndErrorMessageForGetMethod() throws Exception {

        when(productService.getProductById(99L))
                .thenThrow(new IllegalArgumentException("Product not found: 99"));


        mockMvc.perform(get("/api/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found: 99"));

        verify(productService).getProductById(99L);

    }

    @Test
    void ableToCreateProduct() throws Exception {

        Product inputProduct =
                new Product(null,"Tredmill",25000,15);

        Product savedProduct =
                new Product(2L,"Tredmill",25000,15);

        when(productService.createProduct(any(Product.class)))
                .thenReturn(savedProduct);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name":"Tredmill",
                                    "price":25000,
                                    "stock":15
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Tredmill"))
                .andExpect(jsonPath("$.price").value(25000))
                .andExpect(jsonPath("$.stock").value(15));

//        verify(productService).createProduct(any(Product.class));

        verify(productService)
                .createProduct(
                        argThat(product ->
                                product.getName().equals("Tredmill") &&
                                product.getPrice()==25000 &&
                                product.getStock()==15)
                );
    }

    @Test
    void ableToGetProperExceptionAndErrorMessageForCreateProduct() throws Exception {

        when(productService.createProduct(any(Product.class)))
                .thenThrow(new RuntimeException("Product already exists"));

        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name":"Tredmill",
                            "price":25000,
                            "stock":15
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product already exists"));

        verify(productService).createProduct(
                argThat(product ->
                        product.getName().equals("Tredmill") &&
                                product.getPrice()==25000 &&
                                product.getStock()==15
                )
        );
    }
}
