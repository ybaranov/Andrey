//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.07 at 08:53:03 AM MSK 
//


package com.prima.configuration.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplier_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="articul_column" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="product_name_column" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="product_price_column" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="product_quantity_column" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="has_retail_price" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="retail_price_multiplier_percent" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "supplierId",
    "articulColumn",
    "productNameColumn",
    "productPriceColumn",
    "productQuantityColumn",
    "hasRetailPrice",
    "retailPriceMultiplierPercent"
})
@XmlRootElement(name = "root")
public class Root {

    @XmlElement(name = "supplier_id", required = true)
    protected String supplierId;
    @XmlElement(name = "articul_column", required = true)
    protected String articulColumn;
    @XmlElement(name = "product_name_column", required = true)
    protected String productNameColumn;
    @XmlElement(name = "product_price_column", required = true)
    protected String productPriceColumn;
    @XmlElement(name = "product_quantity_column", required = true)
    protected String productQuantityColumn;
    @XmlElement(name = "has_retail_price")
    protected boolean hasRetailPrice;
    @XmlElement(name = "retail_price_multiplier_percent")
    protected Integer retailPriceMultiplierPercent;

    /**
     * Gets the value of the supplierId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * Sets the value of the supplierId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierId(String value) {
        this.supplierId = value;
    }

    /**
     * Gets the value of the articulColumn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticulColumn() {
        return articulColumn;
    }

    /**
     * Sets the value of the articulColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticulColumn(String value) {
        this.articulColumn = value;
    }

    /**
     * Gets the value of the productNameColumn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductNameColumn() {
        return productNameColumn;
    }

    /**
     * Sets the value of the productNameColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductNameColumn(String value) {
        this.productNameColumn = value;
    }

    /**
     * Gets the value of the productPriceColumn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductPriceColumn() {
        return productPriceColumn;
    }

    /**
     * Sets the value of the productPriceColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductPriceColumn(String value) {
        this.productPriceColumn = value;
    }

    /**
     * Gets the value of the productQuantityColumn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductQuantityColumn() {
        return productQuantityColumn;
    }

    /**
     * Sets the value of the productQuantityColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductQuantityColumn(String value) {
        this.productQuantityColumn = value;
    }

    /**
     * Gets the value of the hasRetailPrice property.
     * 
     */
    public boolean isHasRetailPrice() {
        return hasRetailPrice;
    }

    /**
     * Sets the value of the hasRetailPrice property.
     * 
     */
    public void setHasRetailPrice(boolean value) {
        this.hasRetailPrice = value;
    }

    /**
     * Gets the value of the retailPriceMultiplierPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetailPriceMultiplierPercent() {
        return retailPriceMultiplierPercent;
    }

    /**
     * Sets the value of the retailPriceMultiplierPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetailPriceMultiplierPercent(Integer value) {
        this.retailPriceMultiplierPercent = value;
    }

}
