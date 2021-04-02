package aptech.fpt.spring.controller;

import aptech.fpt.spring.entity.Product;
import aptech.fpt.spring.entity.ProductValidator;
import aptech.fpt.spring.filehandle.UploadController;
import aptech.fpt.spring.model.ProductModel;
import aptech.fpt.spring.model.ProductModel2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductModel productModel;
    @Autowired
    private ProductModel2 productModel2;

    @RequestMapping(path = "/product/create", method = RequestMethod.GET)
    public String createProduct(@ModelAttribute Product p) {
        return "product-form";
    }

    @RequestMapping(path = "/product/save", method = RequestMethod.POST)
    public String saveProduct(@Valid Product product, BindingResult result,
                              @RequestParam("myFile") MultipartFile myFile) {
        product.setImgUrl("_");
        new ProductValidator().validate(product, result);
        if (result.hasErrors()) {
            return "product-form";
        }
        try {
            String UPLOADED_FOLDER = UploadController.UPLOADED_FOLDER;
            Path path = Paths.get(UPLOADED_FOLDER + myFile.getOriginalFilename());
            Files.write(path, myFile.getBytes());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        product.setImgUrl("/uploaded/" + myFile.getOriginalFilename());
        productModel.save(product);
        return "redirect:/product/list";
    }

        @RequestMapping(value = "/product/edit",method = RequestMethod.GET)
        public String editProduct(@RequestParam("id") int id,Model model){
            Optional<Product> productEdit = productModel.findById(id);
            productEdit.ifPresent(product -> model.addAttribute("product",product));
            return "product-form-edit";
        }

    @RequestMapping(path = "/product/delete/{id}", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable int id) {
        Optional<Product> optionalProduct = productModel.findById(id);
        if (optionalProduct.isPresent()) {
            productModel.delete(optionalProduct.get());
            return "redirect:/product/list";
        } else {
            return "not-found";
        }
    }

    @RequestMapping(path = "/product/list", method = RequestMethod.GET)
    public String getListProduct(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        Page<Product> pagination = productModel.findProductsByStatus(1, PageRequest.of(page - 1, limit));
        model.addAttribute("pagination", pagination);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);
        model.addAttribute("datetime", Calendar.getInstance().getTime());
        return "product-list";
    }

    @RequestMapping(path = "/product/list2", method = RequestMethod.GET)
    public String getListProduct2(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        model.addAttribute("list", productModel2.findDistinctByName("Apple"));
        return "product-list2";
    }
}
