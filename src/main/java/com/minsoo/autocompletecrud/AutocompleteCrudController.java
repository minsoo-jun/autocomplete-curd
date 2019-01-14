package com.minsoo.autocompletecrud;

import com.minsoo.autocompletecrud.domain.Product;
import com.minsoo.autocompletecrud.domain.ProductPubSub;
import com.minsoo.autocompletecrud.domain.SearchDomain;
import com.minsoo.autocompletecrud.repository.ProductRepository;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.StringHttpMessageConverter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.minsoo.autocompletecrud.constants.Constants.GOOGLE_APPLICATION_CREDENTIALS;

@Controller
public class AutocompleteCrudController {

    @Autowired
    private ProductRepository productRepository;

    private final PubSubProductGateway pubSubProductGateway;

    public AutocompleteCrudController(PubSubProductGateway pubSubProductGateway){
        this.pubSubProductGateway = pubSubProductGateway;
    }

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

        ProductPubSub productPubSub = new ProductPubSub(product.getN_product(), product.getId_sku(), product.getN_popurality());
        System.out.println("Product=" + productPubSub.toString());
        this.pubSubProductGateway.sendProductToPubSub(productPubSub);
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

    @GetMapping(path="/datasync/")
    public String dataSyncForWord(){
        List<Product> productList = productRepository.find1000();

        int i = 0;
        for(Product product: productList){
            List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
            converters.add(new FormHttpMessageConverter());
            converters.add(new StringHttpMessageConverter());

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(converters);

            LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("n_product", product.getN_product());
            map.add("id_sku", product.getId_sku()+"");
            map.add("n_popurality", product.getN_popurality()+"");

            System.out.println("n_product:" + product.getN_product());

            String url = "http://localhost:8888/ecdata/datasync";

            String result = restTemplate.postForObject(url, map, String.class);
            System.out.println(result + " " + i);
            i++;
        }


        return "index";
    }


}
