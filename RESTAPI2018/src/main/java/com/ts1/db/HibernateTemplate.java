package com.ts1.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rest1.dto.Cart;
import com.rest1.dto.Customer;
import com.rest1.dto.OrderHistory;
import com.rest1.dto.Orders;
import com.rest1.dto.Products;
import com.rest1.dto.Review;


public class HibernateTemplate {
	

	private static SessionFactory sessionFactory;
	
	static {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	public static int addObject(Object obj){
		System.out.println("Inside Template...");
		int result=0;
		Transaction tx=null;		
		try {
			Session session=sessionFactory.openSession();
			tx=session.beginTransaction();
			session.save(obj);
			tx.commit();
			result=1;		
		} catch (Exception e) {
			System.out.println("failure...");		
			tx.rollback();			
			e.printStackTrace();
		}		
		return result;
	}
	
	
	public static Object getObjectById(int productId) {
		
	      String queryString = "from Products where productId = :productId";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setInteger("productId", productId);
		  Object queryResult = query.uniqueResult();
		  Products product = (Products)queryResult;
		  return product; 
	}	
	public static Object getCustomerById(int custId) {
		
	      String queryString = "from Customer where custId = :custId";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setInteger("custId", custId);
		  Object queryResult = query.uniqueResult();
		  Customer customer = (Customer)queryResult;
		  return customer; 
	}	
	
	
	
	public static int updateObject(Object obj)
	{
		int result=0;
		
		Transaction tx=null;
		
		try {
			
			Session session=sessionFactory.openSession();
			tx=session.beginTransaction();
			
			session.saveOrUpdate(obj);
			
			tx.commit();
			
			result=1;
			
			
		} catch (Exception e) {
		    
			tx.rollback();
			System.out.println("update failure...");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int deleteObject(Class c,Serializable serializable)
	{
		int result=0;
		
		Session session=sessionFactory.openSession();
		
		Transaction tx=session.beginTransaction();
		
		try {
			
			Object obj=session.get(c,serializable);
			System.out.println(obj);
			System.out.println(serializable);
			
			session.delete(obj);
			
			tx.commit();
			
			result=1;
			
						
		} catch (Exception e) {
			
			e.printStackTrace();
			
			tx.rollback();
			
			System.out.println("delete failure...");
		}
		
		return result;
	}

	
	public static List<Customer> getCustomerListByQuery()
	{
		String query = "from Customer";
		return  sessionFactory.openSession().createQuery(query).list();
		
	}
	public static List<Review> getReviewListByQuery()
	{
		String query = "from Review";
		return  sessionFactory.openSession().createQuery(query).list();
		
	}
	
	public static List<Products> getObjectListByQuery()
	{
		String query = "from Products";
		return sessionFactory.openSession().createQuery(query).list();
		
	}
	
	public static List<Orders> getOrderListByQuery()
	{
		String query = "from Orders";
		return sessionFactory.openSession().createQuery(query).list();
	}
	public static List<Cart> viewMyCart(int custId)
	{
		String queryS="from Cart where custId=:custId";
		return sessionFactory.openSession().createQuery(queryS).setParameter("custId", custId).list();	
		
	}
	public static List<Products> viewMyProducts(int custId)
	{
		String queryS="from Products where custId=:custId";
		return sessionFactory.openSession().createQuery(queryS).setParameter("custId", custId).list();	
		
	}
	
	public static List<Products> getOldProducts(int custId)
	{
		String queryS="from Products where custId !=:custId";
		return sessionFactory.openSession().createQuery(queryS).setParameter("custId", custId).list();	
		
	}
	
	public static List<Products> getNewProducts()
	{
		String productStatus = "new";
		String queryS="from Products where productStatus =:productStatus";
		return sessionFactory.openSession().createQuery(queryS).setParameter("productStatus", productStatus).list();	
		
	}
	
	
	public static Customer getObjectByQuery(String query) {
		return (Customer) sessionFactory.openSession().createQuery(query);
	}

	
	public static int deleteProductAddedByCustomer(Object obj) {
		Session session=sessionFactory.openSession();
		Transaction tx = null;
		int result = 0;
		try {
			session.beginTransaction();
			session.delete(obj);
			session.getTransaction().commit();
			result=1;
		}catch (Exception e) {
			System.out.println("failure...");
			tx.rollback();
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object getObjectById(int custId, String productName) {
		  System.out.println("hiiii....");
	      SessionFactory s = new Configuration().configure().buildSessionFactory();
			Session session = s.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Products where custId = :custId and productName = :productName");
			query.setInteger("custId", custId);
			query.setString("productName", productName);
			//List products = query.list();
			Object queryResult = query.uniqueResult();
			Products product = (Products)queryResult;
			session.getTransaction().commit();
			session.close();
			return product;
	}
	
	//get product by id
	public static Products getProductById(int productId) {
		    System.out.println("hiiii....");
	        SessionFactory s = new Configuration().configure().buildSessionFactory();
			Session session = s.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Products where productId = :productId");
			query.setInteger("productId", productId);
			Object queryResult = query.uniqueResult();
			Products product = (Products)queryResult;
			session.getTransaction().commit();
			session.close();
			return product;
	}
	
	//place order
	public static double placeOrder(int custId) {
		  System.out.println("hiiii....");
	      SessionFactory s = new Configuration().configure().buildSessionFactory();
			Session session = s.openSession();
			session.beginTransaction();
			Query query = session.createQuery("select sum(price) from Cart where custId = :custId");
			query.setInteger("custId", custId);
			Object queryResult = query.uniqueResult();
			double result = (double) queryResult;
			System.out.println(result);
			session.getTransaction().commit();
			session.close();
			return result;
	}
	
	//customer login
	public static Customer customerLogin(String loginId, String password) {
		  System.out.println("hiiii....");
	      SessionFactory s = new Configuration().configure().buildSessionFactory();
			Session session = s.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Customer where loginId = :loginId and password = :password");
			query.setString("loginId", loginId);
			query.setString("password", password);
			Object queryResult = query.uniqueResult();
			Customer customer = (Customer)queryResult;
			session.getTransaction().commit();
			//session.close();
			return customer;
	}
	
	//deliver order
	public static Object getOrderById(int orderId) {
		  System.out.println("hiiii....");
	      SessionFactory s = new Configuration().configure().buildSessionFactory();
			Session session = s.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Orders where orderId = :orderId");
			query.setInteger("orderId", orderId);
			Object queryResult = query.uniqueResult();
			Orders order = (Orders)queryResult;
			order.setOrderStatus("Delivered");
			session.getTransaction().commit();
			session.close();
			return order;
	}
	//search product
	public static Products searchProduct(String productName) {
		  String queryString = "from Products where productName = :productName";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setString("productName", productName);
		  Object queryResult = query.uniqueResult();
		  Products product = (Products)queryResult;
		  return product;
	}
	
	public static Cart getFromCart(int productId, int custId) {
		String queryString = "from Cart where productId = :productId and custId = :custId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("productId", productId);
		query.setInteger("custId", custId);
		Object queryResult = query.uniqueResult();
		Cart cart = (Cart)queryResult;
		return cart;
	}
	//remove from cart
	public static void removeFromCart(int cartId) {
		deleteObject(Cart.class, cartId);
	}
	
	//delete from cart
	public static void deletefromCart(int custId) {
		
		SessionFactory s = new Configuration().configure().buildSessionFactory();
		Session session = s.openSession();
		session.beginTransaction();
		Query qry = session.createQuery("delete from Cart where custId=:custId");
		qry.setParameter("custId",custId);
		int res = qry.executeUpdate();

		System.out.println("deleted from cart"+res);
		session.getTransaction().commit();
	}
	
	/*public static void deliverOrders() {
		List<Orders> orders= new ArrayList<Orders>();
		System.out.println("in deliver orders");
		String queryString1 = "from Orders";
		Query query1 = sessionFactory.openSession().createQuery(queryString1);
		orders = query1.list();
		for(Orders order: orders) {
			if(order.getDateOfOrder().compareTo(new Date()) <= 0) {
				order.setOrderStatus("delivered");
				updateObject(order);
			}
		}
	}*/
	
	public static List<OrderHistory> viewMyOrders(int custId) {
		String queryString1 = "from OrderHistory where orderId in(select orderId from Orders where custId =:custId)";
		Query query1 = sessionFactory.openSession().createQuery(queryString1).setParameter("custId", custId);
		List<OrderHistory> orderHistory = new ArrayList<OrderHistory>();
		orderHistory = query1.list();
		return orderHistory;
	}
	public static List viewCountOfOrders(int custId) {
		String queryString1 = "select count(orderId), orderId from orderhistory where orderId in(select orderId from orders where custId =:custId) group by orderId";
		SQLQuery query1 = (SQLQuery) sessionFactory.openSession().createSQLQuery(queryString1).setParameter("custId", custId);
		return query1.list();
	}
	
	public static void changeProductCount(int productId, int count) {
		Products product = new Products();
		product = getProductById(productId);
		product.setQuantityAdded(product.getQuantityAdded()-count);
		updateObject(product);
		}

	
	public static void deliverOrders() {
		List<Orders> orders= new ArrayList<Orders>();
		String queryString1 = "from Orders";
		Query query1 = sessionFactory.openSession().createQuery(queryString1);
		orders = query1.list();
		for(Orders order: orders) {
			if( order.getDateOfOrder().compareTo(new Date()) <= 0 && order.getOrderStatus().equals("Pending") ) {
				order.setOrderStatus("delivered");
				updateObject(order);

		//changing the product count in products table
				List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
				orderHistoryList = viewMyOrders(order.getCustomer().getCustId());

				int len = orderHistoryList.size();
				for(int i = 0;i < len;i++){

					Products product = new Products();
					product = orderHistoryList.get(i).getProducts();

					product.setQuantityAdded(product.getQuantityAdded()- orderHistoryList.get(i).getQuantity());
					if(product.getQuantityAdded() == orderHistoryList.get(i).getQuantity()){
						deleteObject(Products.class, product.getProductId());
					} else {
						updateObject(product);
					}
				}
			}
		}
	}
			
}	
			
			
			
			
			
			
			
			
			
			
	 // end



		


















/*public static Object getObjectByUserPass(String loginId,String password) {

String queryString = "from Employee where email = :loginId and password =:password";
  Query query = sessionFactory.openSession().createQuery(queryString);
  query.setString("loginId", loginId);
  query.setString("password", password);
  Object queryResult = query.uniqueResult();
  Employee employee = (Employee)queryResult;
  return employee; 
}

	
	public static Object getObject(Class c,Serializable serializable)
	{
		Object obj=null;	
		try {			
			return sessionFactory.openSession().get(c,serializable);			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		return obj;
	}  
	
	   
	public static Object getObjectByEmail(String email) {
		
		String queryString = "from customer where email = :email";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setString("email", email);
		  Object queryResult = query.uniqueResult();
		  Customer customer = (Customer)queryResult;
		  return customer; 
	}
	public static Object getObjectByLoginId(String loginId) {
		
		  String queryString = "from customer where loginId = :loginId";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setString("loginId", loginId);
		  Object queryResult = query.uniqueResult();
		  Customer customer = (Customer)queryResult;
		  return customer; 
	}
	
	
	public static List<Object> getObjectListByName(Class c, String columName, String value) {
		Session session=sessionFactory.openSession();
		  Criteria criteria = session.createCriteria(c);
			Criterion nameCriterion = Restrictions.eq(columName, value);
			criteria.add(nameCriterion);
			return criteria.list();
	}
		*/

