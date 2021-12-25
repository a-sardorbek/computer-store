package com.system.ws.resource;

import com.system.ws.domain.entity.Product;
import com.system.ws.exception.ProductNotFoundException;
import com.system.ws.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/","/product"})
public class ProductResource {

    private ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> allProducts(){
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/findProduct/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId")String productId) throws ProductNotFoundException {
        Optional<Product> product = productService.findByProductId(productId);
        if(product.isPresent()) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else {
            throw new ProductNotFoundException("No such product with product id: "+productId);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addNewProduct(@RequestParam("brand")String brand,
                                                 @RequestParam("processor")String processor,
                                                 @RequestParam("graphicsCard")String graphicsCard,
                                                 @RequestParam("diagonalScreen")String diagonalScreen,
                                                 @RequestParam("quantity")String quantity,
                                                 @RequestParam("cost")String cost) {

        Product newProduct = productService.addNewProduct(brand
                ,processor
                ,graphicsCard
                ,diagonalScreen
                ,Integer.parseInt(quantity)
                ,Double.parseDouble(cost));
        return new ResponseEntity<>(newProduct,HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId")String productId,
                                           @RequestParam("brand")String brand,
                                           @RequestParam("processor")String processor,
                                           @RequestParam("graphicsCard")String graphicsCard,
                                           @RequestParam("diagonalScreen")String diagonalScreen,
                                           @RequestParam("quantity")String quantity,
                                           @RequestParam("cost")String cost){
      Product updatedProduct =  productService.updateProduct(brand,processor,graphicsCard,diagonalScreen,Integer.parseInt(quantity),Double.parseDouble(cost),productId);
      return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }


}
