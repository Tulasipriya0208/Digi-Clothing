package com.rest1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.rest1.dto.Cart;
import com.rest1.dto.Customer;
import com.rest1.dto.OrderHistory;
import com.rest1.dto.Orders;
import com.rest1.dto.Products;
import com.rest1.dto.Review;
import com.rest1.dto.SentemailTLS;
import com.ts1.dao.AdminDAO;
import com.ts1.dao.CustomerDAO;
import com.ts1.dao.ProductsDAO;
import com.ts1.dao.ReviewDAO;
import com.ts1.db.HibernateTemplate;
@Path("myresource1")
public class MyResource1 {
	
	// customer methods
	@Path("regCustomer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String regCustomer(Customer customer) {
		System.out.println("Data Recieved in Register : " + customer);
		CustomerDAO custDao = new CustomerDAO();
		custDao.register(customer);
    	return null;
    }
	
	@Path("Review")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String review(Review review) {
		System.out.println("Data Recieved in Register : " + review);
		ReviewDAO reviewDao = new ReviewDAO();
		reviewDao.review(review);
    	return null;
    }
	
	@Path("deliverOrders")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public void deliverOrders() {
		HibernateTemplate.deliverOrders();
	}
	
	
	@Path("updateCustomer")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateCustomer(Customer customer) {
		CustomerDAO cusDao = new CustomerDAO();
		cusDao.updateCustomer(customer);
		return "updated";
	}
	
	@Path("updateProducts")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateProducts(Products product) {
		ProductsDAO proDao = new ProductsDAO();
		proDao.updateProducts(product);
		return "updated";
	}
	
	
	@Path("deleteMyProductAdded/{productId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
	public String deleteMyProduct(@PathParam("productId") int productId){
		System.out.println("delete..");
		CustomerDAO custDao = new CustomerDAO();
		custDao.deleteMyProduct(productId);
		return null;
		
	}	
	
	@Path("removeFromCart/{cartId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
	public String removeFromCart(@PathParam("cartId") int cartId){
		HibernateTemplate.removeFromCart(cartId);
		return null;
	}	
	
	
	
	@Path("addItemToCart/{custId}/{productId}/{quantity}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public String addToCart(@PathParam("custId") int custId,@PathParam("productId")  int productId, @PathParam("quantity") int quantity) {
		Customer customer = new Customer();
		Products b = new Products();
		CustomerDAO custDao = new CustomerDAO();
		custDao.addItemToCart(custId, productId, quantity);
		
    	return null;
    }
	
	
	@Path("placeOrder")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrder(int custId) {
		Customer customer = new Customer();
		Cart cart = new Cart();
		CustomerDAO custDao = new CustomerDAO();
		custDao.placeOrder(custId);
		customer=custDao.getcustomerbyid(custId);
		System.out.println(customer);
    	SentemailTLS message = new SentemailTLS();
    	message.digiclothing(customer);
		return null;
    }
	@Path("searchProduct/{productName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Products searchProduct(@PathParam("productName") String productName) {
    	return HibernateTemplate.searchProduct(productName);
    }
	
	@Path("customerLogin/{loginId}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer customerLogin(@PathParam("loginId") String loginId,@PathParam("password")  String password) {
		CustomerDAO custDao = new CustomerDAO();
		Customer c = custDao.customerLogin(loginId, password);
		if(c == null) {
			return null;
		}
		else {
			System.out.println(c);
			return c;
		}
	}
	@Path("viewMyCart/{custId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cart> viewMyCart(@PathParam("custId") int custId) {
		System.out.println(custId);
		CustomerDAO custDao = new CustomerDAO();
		//List <CartItems> cartList = custDao.viewMyCart(custId);
		List<Cart> cartList = HibernateTemplate.viewMyCart(custId);
		System.out.println(cartList);
		return cartList;
		
	}
	
	@Path("viewMyOrders/{custId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderHistory> viewMyOrders(@PathParam("custId") int custId) {
		System.out.println(custId);
		CustomerDAO custDao = new CustomerDAO();
		List<OrderHistory> orderList = HibernateTemplate.viewMyOrders(custId);
		System.out.println(orderList);
		return orderList;
		
	}
	
	@Path("viewCountOfOrders/{custId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderHistory> viewCountOfOrders(@PathParam("custId") int custId) {
		System.out.println(custId);
		List list = HibernateTemplate.viewCountOfOrders(custId);
		System.out.println(list);
		return list;
		
	}
	
	
	@Path("viewMyProducts/{custId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Products> viewMyProducts(@PathParam("custId") int custId){
		CustomerDAO custDao = new CustomerDAO();
		List<Products> productsList = custDao.viewMyProducts(custId);	
		System.out.println(productsList);
		return productsList;		
	}
	
	@Path("getProductById/{productId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Products getProductById(@PathParam("productId") int productId){
		CustomerDAO custDao = new CustomerDAO();
		Products product = custDao.getProductById(productId);	
		System.out.println(product);
		return product;		
	}
	
	//Admin methods
	
	
	@Path("deleteProductAddedByAdmin")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteProductAddedByAdmin(){
		Products b = new Products();
		int ProductId=3;
		AdminDAO adminDao = new AdminDAO();
		adminDao.deleteProductAddedByAdmin(ProductId);
		return null;
		
	}
	
	@Path("viewAllCustomers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Customer> viewAllCustomers(){
		AdminDAO adminDao = new AdminDAO();
		List<Customer> empList = adminDao.viewAllCustomers();	
		System.out.println(empList);
		return empList;	
			
	}
	
	@Path("viewAllReviews")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Review> viewAllReviews(){
		AdminDAO adminDao = new AdminDAO();
		List<Review> empList = adminDao.viewAllReviews();	
		System.out.println(empList);
		return empList;	
			
	}
	
	@Path("viewAllProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Products> viewAllProducts(){
		AdminDAO adminDao = new AdminDAO();
		List<Products> productsList = adminDao.viewAllProducts();	
		System.out.println(productsList);
		return productsList;		
	}
	
	
	
	@Path("getOldProducts/{custId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Products> getOldProducts(@PathParam("custId") int custId){
		System.out.println(custId);
		List<Products> productsList = HibernateTemplate.getOldProducts(custId);	
		System.out.println(productsList);
		return productsList;		
	}
	
	@Path("getNewProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Products> getNewProducts(){
		List<Products> productsList = HibernateTemplate.getNewProducts();	
		System.out.println(productsList);
		return productsList;		
	}
	
	@Path("viewAllOrders")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Orders> viewAllOrders(){
		AdminDAO adminDao = new AdminDAO();
		List<Orders> orderList = adminDao.viewAllOrders();
		return orderList;
		
	}
	
	@Path("deliverOrder")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String deliverOrder(int orderId) {
		AdminDAO adminDao = new AdminDAO();
		adminDao.deliverOrder(orderId);
		return "order delivered";
		
	}
	
	

	
	@POST
	@Path("/uploadImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadImage(@FormDataParam("Image") InputStream fileInputStream,
			@FormDataParam("Image") FormDataContentDisposition formDataContentDisposition,
			@FormDataParam("productName") String productName,
			@FormDataParam("description") String description, 
			@FormDataParam("price") double price,
			@FormDataParam("productStatus") String productStatus, 
			@FormDataParam("quantityAdded") int quantityAdded,
			@FormDataParam("category") String category,
			@FormDataParam("size") String size,
			@FormDataParam("custId") int custId) throws IOException {
			
		System.out.println("Inside Upload Image Recieved...."); 
		int read = 0;
		byte [] bytes = new byte[1024];
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String pathArr[] = path.split("/WEB-INF/classes");
		FileOutputStream out = new FileOutputStream(new File(pathArr[0] + "/image", formDataContentDisposition.getFileName()));
		while((read = fileInputStream.read(bytes)) != -1) {
			out.write(bytes,0,read);
		}
		out.flush();
		out.close();
		System.out.println("custId:"+ custId);
		
		CustomerDAO cdao= new CustomerDAO();
		Customer c = new Customer();
		
		c = cdao.viewCustomerProfile(custId);
		Products product = new Products();
		product.setProductName(productName);
		product.setDescription(description);
		product.setPrice(price);
		product.setProductStatus(productStatus);
		product.setCategory(category);
		product.setSize(size);

		product.setUploadedDate(new Date());
		product.setQuantityAdded(quantityAdded);
		product.setCustomer(c);
		product.setImageName(formDataContentDisposition.getFileName());
		
		AdminDAO adminDao = new AdminDAO();
		adminDao.addProductByAdmin(product);
		
		
		if(custId !=0) {
		CustomerDAO custDao = new CustomerDAO();
		c=custDao.getcustomer(custId);
		System.out.println(c);
    	SentemailTLS message = new SentemailTLS();
    	message.upload(c);
		}
		
	}
    
}



