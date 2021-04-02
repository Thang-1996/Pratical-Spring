package aptech.fpt.spring.model;

import aptech.fpt.spring.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductModel2 extends PagingAndSortingRepository<Product, Integer> {
    Page<Product> findByNameAndPrice(String name, int price, Pageable pageable);

    List<Product> findDistinctByName(String name);

}
