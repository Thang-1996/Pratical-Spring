package aptech.fpt.spring.model;

import aptech.fpt.spring.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductModel
        extends PagingAndSortingRepository<Product, Integer> {

    Page<Product> findProductsByPrice(int price, Pageable pageable);

    Page<Product> findProductsByStatus(int status, Pageable pageable);

    Page<Product> findByName(String name, Pageable pageable);
}
