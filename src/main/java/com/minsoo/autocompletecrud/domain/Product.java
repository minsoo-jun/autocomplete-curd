package com.minsoo.autocompletecrud.domain;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

public class Product {

    public Product(int id_sku, String cd_manufacturer, String cd_model, String cd_type
    ,String k_upc, double n_price, String n_product, double n_shipping, String w_description, String w_image
    ,String w_url, int n_popurality){
        this.id_sku = id_sku;
        this.cd_manufacturer = cd_manufacturer;
        this.cd_model = cd_model;
        this.cd_type = cd_type;
        this.k_upc = k_upc;
        this.n_price = n_price;
        this.n_product = n_product;
        this.n_shipping = n_shipping;
        this.w_description = w_description;
        this.w_image = w_image;
        this.w_url= w_url;
        this.n_popurality = n_popurality;
    }
    private int id_sku = 0;
    private String cd_manufacturer;
    private String cd_model;
    private String cd_type;
    private String k_upc;
    private double n_price;
    private String n_product;
    private double n_shipping = 0;
    private String w_description;
    private String w_image;
    private String w_url;
    private int n_popurality = 0;

    public int getId_sku() {
        return id_sku;
    }

    public void setId_sku(int id_sku) {
        this.id_sku = id_sku;
    }

    public String getCd_manufacturer() {
        return cd_manufacturer;
    }

    public void setCd_manufacturer(String cd_manufacturer) {
        this.cd_manufacturer = cd_manufacturer;
    }

    public String getCd_model() {
        return cd_model;
    }

    public void setCd_model(String cd_model) {
        this.cd_model = cd_model;
    }

    public String getCd_type() {
        return cd_type;
    }

    public void setCd_type(String cd_type) {
        this.cd_type = cd_type;
    }

    public String getK_upc() {
        return k_upc;
    }

    public void setK_upc(String k_upc) {
        this.k_upc = k_upc;
    }

    public double getN_price() {
        return n_price;
    }

    public void setN_price(double n_price) {
        this.n_price = n_price;
    }

    public String getN_product() {
        return n_product;
    }

    public void setN_product(String n_product) {
        this.n_product = n_product;
    }

    public double getN_shipping() {
        return n_shipping;
    }

    public void setN_shipping(double n_shipping) {
        this.n_shipping = n_shipping;
    }

    public String getW_description() {
        return w_description;
    }

    public void setW_description(String w_description) {
        this.w_description = w_description;
    }

    public String getW_image() {
        return w_image;
    }

    public void setW_image(String w_image) {
        this.w_image = w_image;
    }

    public String getW_url() {
        return w_url;
    }

    public void setW_url(String w_url) {
        this.w_url = w_url;
    }

    public int getN_popurality() {
        return n_popurality;
    }

    public void setN_popurality(int n_popurality) {
        this.n_popurality = n_popurality;
    }
}
