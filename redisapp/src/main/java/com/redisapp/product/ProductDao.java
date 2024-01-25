package com.redisapp.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ProductDao {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String HASH_KEY= "Product";

    public Product save(Product product){
        redisTemplate.opsForHash().put(HASH_KEY,product.getId(),product);
        return product;
    }

    public List<Product> findAllProducts(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Product getProductById(int productId){
        log.info("Getting Data from RedisDB...");
        return (Product) redisTemplate.opsForHash().get(HASH_KEY,productId);
    }

    public String deleteProductById(int productId){
        redisTemplate.opsForHash().delete(HASH_KEY,productId);
        return "Removed SuccessFully"+productId;
    }
}
