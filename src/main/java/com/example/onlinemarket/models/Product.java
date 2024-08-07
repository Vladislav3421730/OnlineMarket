package com.example.onlinemarket.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Название товара пустое")
    @Column(name = "Title")
    private String Title;

    @Column(name = "Description",columnDefinition = "text")
    @NotBlank(message = "Добавьте описание")
    private String Description;

    @Column(name="Coast")
    private double Coast;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "product")
    private List<Image> imageList=new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void AddImageToProductImagesList(Image image){
        image.setProduct(this);
        imageList.add(image);
    }

}
