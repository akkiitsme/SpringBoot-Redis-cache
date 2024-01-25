package com.redisapp.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productDao.save(product), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProductList(){
        return new ResponseEntity<>(productDao.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 100")
    public Product findProductById(@PathVariable int id){
        return productDao.getProductById(id);
        //unless condition is used to add those record in cache whose price is greater than 100
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key="#id",value ="Product")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        return new ResponseEntity<>(productDao.deleteProductById(id), HttpStatus.OK);
    }
}
