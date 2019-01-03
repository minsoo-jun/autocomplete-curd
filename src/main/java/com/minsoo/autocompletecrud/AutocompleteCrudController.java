package com.minsoo.autocompletecrud;

import com.minsoo.autocompletecrud.domain.Product;
import com.minsoo.autocompletecrud.domain.SearchDomain;
import com.minsoo.autocompletecrud.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AutocompleteCrudController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path="/save/")
    public String addProduct(@Valid Product product, BindingResult result, Model model){
        if (result.hasErrors()) {
            System.out.println("--->" + result.hasErrors());
            System.out.println("--->" + result.getAllErrors().toString());
            return "form-register";
        }
        model.addAttribute("product", productRepository.saveProduct(product));
        return "form-info";
    }

    @GetMapping(path="/add/")
    public String newProdcut(Model model){
        return "form-register";
    }

    @GetMapping(path="/")
    public String getAllProducts(Model model){
        model.addAttribute("products", productRepository.findInit());
        List<Product> products = productRepository.findInit();
        //products.forEach(pd -> System.out.println(pd.getId_sku()));
        return "index";
    }

    @GetMapping(path="/info/{sku}")
    public String getProductInfo(@PathVariable("sku") int sku, Model model){
        //return productRepository.findAll();
        model.addAttribute("product", productRepository.findBySku(sku));
        return "form-info";
    }

    @GetMapping(path="/edit/{sku}")
    public String editProductInfo(@PathVariable("sku") int sku, Model model){
        //return productRepository.findAll();
        model.addAttribute("product", productRepository.findBySku(sku));
        return "form-edit";
    }

    @PostMapping("/update/")
    public String updateProductInfo(Product product, Model model){

        /*
        System.out.println(product.getId_sku());
        System.out.println(product.getN_product());
        System.out.println(product.getCd_manufacturer());
        System.out.println(product.getCd_model());
        System.out.println(product.getCd_type());
        System.out.println(product.getN_price());
        System.out.println(product.getN_shipping());
        System.out.println(product.getW_description());
        System.out.println(product.getW_image());
        System.out.println(product.getW_url());
        System.out.println(product.getN_popurality());
        */
        model.addAttribute("product", productRepository.updateProduct(product));
        return "form-info";
    }

    @GetMapping(path="/find/")
    public String findProduct(Model model){
        return "form-search";
    }
    @GetMapping(path="/search/")
    public String searchProduct(@RequestParam(value = "keyword") String keyword, Model model){
        System.out.println("Search Keyword: " + keyword);
        if(StringUtils.isNotEmpty(keyword)) {
            model.addAttribute("products", productRepository.findByName(keyword));
        }
        return "form-search";
    }
}
