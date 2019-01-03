package com.minsoo.autocompletecrud.repository;

import com.minsoo.autocompletecrud.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.autocompletecrud.constants.Constants.DEFALUT_SEARCH_LIMIT;

@Repository
public class ProductRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String FIND_BY_SKU = "select * from m_product where id_sku = ?";

    private final String FIND_ALL = "select id_sku, cd_manufacturer, cd_model, cd_type, k_upc, n_price, n_product, n_shipping, w_description, w_image, w_url, n_popurality from m_product order by n_popurality desc limit " + DEFALUT_SEARCH_LIMIT;

    private final String GET_MAX_SKU = "select max(id_sku)+1 sku from m_product";

    private final String UPDATE_BY_SKU = "update m_product set cd_manufacturer=?"
            + ", cd_model=?"
            + ", cd_type=?"
            + ", k_upc=?"
            + ", n_price=?"
            + ", n_product=?"
            + ", n_shipping=?"
            + ", w_description=?"
            + ", w_image=?"
            + ", w_url=?"
            + ", n_popurality=?"
            + " where id_sku=?";

    private final String FIND_BY_NAME = "select * from m_product where n_product like ? order by n_popurality desc";

    public int getSku(){
        return jdbcTemplate.queryForObject(GET_MAX_SKU, (rs, rowNum) -> new Integer(rs.getInt("sku")));
    }

    public List<Product> findInit(){

        // find all products
        List<Product> result = jdbcTemplate.query(FIND_ALL
                , (rs, rowNum) -> new Product(rs.getInt("id_sku")
                ,rs.getString("cd_manufacturer")
                ,rs.getString("cd_model")
                ,rs.getString("cd_type")
                ,rs.getString("k_upc")
                ,rs.getDouble("n_price")
                ,rs.getString("n_product")
                ,rs.getDouble("n_shipping")
                ,rs.getString("w_description")
                ,rs.getString("w_image")
                ,rs.getString("w_url")
                ,rs.getInt("n_popurality")));

        return result;
    }

    public List<Product> findByName(String keyword){
        List<Product> result = jdbcTemplate.query(FIND_BY_NAME, new Object[]{"%" + keyword + "%"}, (rs, rownum) -> new Product(rs.getInt("id_sku")
                ,rs.getString("cd_manufacturer")
                ,rs.getString("cd_model")
                ,rs.getString("cd_type")
                ,rs.getString("k_upc")
                ,rs.getDouble("n_price")
                ,rs.getString("n_product")
                ,rs.getDouble("n_shipping")
                ,rs.getString("w_description")
                ,rs.getString("w_image")
                ,rs.getString("w_url")
                ,rs.getInt("n_popurality")));
        return result;
    }

    public Product findBySku(int sku){
        return jdbcTemplate.queryForObject(FIND_BY_SKU, new Object[]{sku}, (rs, rowNum) -> new Product(rs.getInt("id_sku")
                ,rs.getString("cd_manufacturer")
                ,rs.getString("cd_model")
                ,rs.getString("cd_type")
                ,rs.getString("k_upc")
                ,rs.getDouble("n_price")
                ,rs.getString("n_product")
                ,rs.getDouble("n_shipping")
                ,rs.getString("w_description")
                ,rs.getString("w_image")
                ,rs.getString("w_url")
                ,rs.getInt("n_popurality")));
    }

    public Product saveProduct(Product pd){
        int sku = getSku();
        System.out.println("Issued SKU =" + sku);
        pd.setId_sku(sku);
        int result = jdbcTemplate.update("insert into m_product(id_sku,cd_manufacturer,cd_model,cd_type,k_upc,n_price,n_product,n_shipping,w_description,w_image,w_url,n_popurality) value(?,?,?,?,?,?,?,?,?,?,?,?)"
        ,pd.getId_sku(),pd.getCd_manufacturer(),pd.getCd_model(),pd.getCd_type(),pd.getK_upc(),pd.getN_price(),pd.getN_product(),pd.getN_shipping(),pd.getW_description()
        ,pd.getW_image(),pd.getW_url(),pd.getN_popurality());

        //System.out.println("Result=" + result);

        return jdbcTemplate.queryForObject(FIND_BY_SKU, new Object[]{pd.getId_sku()}, (rs, rowNum) -> new Product(rs.getInt("id_sku")
                ,rs.getString("cd_manufacturer")
                ,rs.getString("cd_model")
                ,rs.getString("cd_type")
                ,rs.getString("k_upc")
                ,rs.getDouble("n_price")
                ,rs.getString("n_product")
                ,rs.getDouble("n_shipping")
                ,rs.getString("w_description")
                ,rs.getString("w_image")
                ,rs.getString("w_url")
                ,rs.getInt("n_popurality")));
    }

    public Product updateProduct(Product pd){
        jdbcTemplate.update(UPDATE_BY_SKU, pd.getCd_manufacturer(),pd.getCd_model(),pd.getCd_type(),pd.getK_upc(),pd.getN_price(),pd.getN_product(),pd.getN_shipping(),pd.getW_description(),pd.getW_image(),pd.getW_url(),pd.getN_popurality(),pd.getId_sku());
        return findBySku(pd.getId_sku());
    }
}
