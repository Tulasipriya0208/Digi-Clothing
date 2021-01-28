package com.ts1.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rest1.dto.Products;
import com.rest1.dto.Cart;
import com.rest1.dto.CartItems;
import com.rest1.dto.Customer;
import com.rest1.dto.OrderHistory;
import com.rest1.dto.Orders;
import com.ts1.db.HibernateTemplate;



public class CustomerDAO {
	private int orderId;
	private SessionFactory factory = null;
	private static SessionFactory sessionFactory;
	public int register(Customer customer) {
		return HibernateTemplate.addObject(customer);
	}
	public int updateCustomer(Customer customer) {
		return HibernateTemplate.updateObject(customer);
	}
	public Customer viewCustomerProfile(int custId) {
		return (Customer) HibernateTemplate.getCustomerById(custId);	
	}
	
	public List<Products> viewMyProducts(int custId) {
		return HibernateTemplate.viewMyProducts(custId);
	}
	
	public int deactivateMyAccount(int custId) {
		return HibernateTemplate.deleteObject(Customer.class, custId);
	}
	
	public int deleteMyProduct(int productId) {
		HibernateTemplate.deleteObject(Cart.class, productId);
		return HibernateTemplate.deleteObject(Products.class, productId);
	}
	public int addItemToCart(int custId, int productId, int quantity) {
		Customer c = new Customer();
		Products b = new Products();
		
		c = (Customer) HibernateTemplate.getCustomerById(custId);	
		b = (Products) HibernateTemplate.getObjectById(productId);
		
        double price = (b.getPrice()*quantity); 
		Cart cart = new Cart();
		cart.setProducts(b);
		cart.setCustomer(c);
		cart.setCartQuantity(quantity);
		cart.setPrice(price);
		cart.setCustomer(c);
		return HibernateTemplate.addObject(cart);
		
		
		
		
	}
	
	
	public Customer customerLogin(String loginId, String password) {
		return (Customer) HibernateTemplate.customerLogin(loginId, password);
	}
	
	public void placeOrder(int custId) {
		Customer c = new Customer();
		c = (Customer) HibernateTemplate.getCustomerById(custId);
		int totalPrice = (int) HibernateTemplate.placeOrder(custId);
		   Orders orders = new Orders();
		   orders.setTotalPrice(totalPrice);
		   orders.setOrderStatus("Pending..");
		   
		   Calendar c1 = Calendar.getInstance();
		   c1.setTime(new Date()); // Now use today date.
		   c1.add(Calendar.DATE, 1);
		 
		   c1.getTime();
		   orders.setDateOfOrder(c1.getTime());
		   orders.setCustomer(c);
		   HibernateTemplate.addObject(orders);
			List<Cart> cartList = HibernateTemplate.viewMyCart(custId);
			
			OrderHistory orderHistory = new OrderHistory();
			for(int i = 0;i < cartList.size();i++){
				Cart cart = cartList.get(i);
				orderHistory.setProducts(cart.getProducts());
				orderHistory.setQuantity(cart.getCartQuantity());
				orderHistory.setOrders(orders);
				HibernateTemplate.addObject(orderHistory);
				HibernateTemplate.changeProductCount(cart.getProducts().getProductId(),cart.getCartQuantity());
			}
		   HibernateTemplate.deletefromCart(custId);
	}
	
	public Products getProductById(int productId) {
		return HibernateTemplate.getProductById(productId);
	}
	public Customer getcustomerbyid(int custId) {
		// TODO Auto-generated method stub
		Customer customer=new Customer();
		customer = (Customer) HibernateTemplate.getCustomerById(custId);
		return customer;
	}
	public Customer getcustomer(int custId) {
		// TODO Auto-generated method stub
		Customer customer=new Customer();
		customer = (Customer) HibernateTemplate.getCustomerById(custId);
		return customer;
	}
}




