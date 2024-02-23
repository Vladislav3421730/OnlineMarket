package com.example.onlinemarket.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Long id;

        @Column(name = "name")
        private String name;

        @Column(name="OriginalFileName")
        private String OriginalFileName;

        @Column(name="Size")
        private Long Size;

        @Column(name = "contentType")
        private String contentType;

        @Lob
        @Column(name = "bytes", columnDefinition = "longblob")
        private byte[] bytes;

        @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
        @JoinColumn(name="product_id")
        private Product product;

        @OneToOne(cascade = CascadeType.REFRESH,fetch=FetchType.LAZY)
        public User user;
}
