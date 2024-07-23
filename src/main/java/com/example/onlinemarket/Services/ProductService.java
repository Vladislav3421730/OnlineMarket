package com.example.onlinemarket.Services;

import com.example.onlinemarket.Repositories.ProductRepository;
import com.example.onlinemarket.exceptions.DataNotFoundException;
import com.example.onlinemarket.models.Image;
import com.example.onlinemarket.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public void saveProduct(Product product,Principal principal, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            product.AddImageToProductImagesList(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.AddImageToProductImagesList(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.AddImageToProductImagesList(image3);
        }
        product.setUser(userService.getUserByPrincipal(principal));
        productRepository.save(product);
    }

    public static Image toImageEntity(MultipartFile file) throws IOException {
        return  Image.builder()
                .name(file.getName())
                .OriginalFileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .Size(file.getSize())
                .bytes(file.getBytes())
                .build();
    }

    public void DeleteProductById(Long id){
        productRepository.deleteById(id);
    }
    public List<Product> AllProducts(String sort,String Search) {
        List<Product> products = productRepository.findAll();
        if(Search!=null){
            products.removeIf(x->!x.getTitle().contains(Search));
            return products;
        }
        switch (sort) {
            case "title":products.sort((x,y)->x.getTitle().compareTo(y.getTitle()));
            break;
            case "Cheap":products.sort((x,y)-> (int) (x.getCoast()-y.getCoast()));
            break;
            case "Expensive":products.sort((x,y)->(int) (y.getCoast()-x.getCoast()));
        }
        return products;
    }

    public Product FindProductById(Long id){
        return productRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("product with id " + id+ " not found"));
    }
}
