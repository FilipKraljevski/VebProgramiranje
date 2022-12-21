package mk.ukim.finki.av1wp.service.impl;

import mk.ukim.finki.av1wp.model.Category;
import mk.ukim.finki.av1wp.model.Manufacturer;
import mk.ukim.finki.av1wp.model.Product;
import mk.ukim.finki.av1wp.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.av1wp.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.av1wp.repository.jpa.CategoryRepository;
import mk.ukim.finki.av1wp.repository.jpa.ManufacturerRepository;
import mk.ukim.finki.av1wp.repository.jpa.ProductRepository;
import mk.ukim.finki.av1wp.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        productRepository.deleteByName(name);
        return Optional.of(productRepository.save(new Product(name, price, quantity, category, manufacturer)));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
