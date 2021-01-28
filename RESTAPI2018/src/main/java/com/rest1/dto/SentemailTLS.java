package com.rest1.dto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class SentemailTLS {
public void digiclothing(Customer customer){

System.out.println("In mail sending");

       final String username = "digiclothing2020@gmail.com";
       final String password = "@webshop02";
       
       String sendMessage="Dear "+customer.getCustName()+",\n"+
    		   "Your order is confirmed !!! :) Thank you for your order from digi-clothing";
       
       Properties prop = new Properties();
       prop.put("mail.smtp.host", "smtp.gmail.com");
       prop.put("mail.smtp.port", "587");
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.starttls.enable", "true"); //TLS

       Session session = Session.getInstance(prop,
               new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

       try {

           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress( "digiclothing2020@gmail.com"));
           message.setRecipients(
                   Message.RecipientType.TO,
                   InternetAddress.parse(customer.getEmail())
           );
           message.setSubject("Confirmation mail ");
           message.setText(sendMessage);

           Transport.send(message);

           System.out.println("Done");

       } catch (MessagingException e) {
    	   
    	   throw new RuntimeException(e);
       }
   }
public void upload(Customer customer){

System.out.println("In mail sending");

       final String username = "digiclothing2020@gmail.com";
       final String password = "@webshop02";
       
       String sendMessage="Dear "+customer.getCustName()+",\n"
       		+ "Your product is successfully uploaded.Admin will get back to you when your product is purchased.\n"
       		+ "If the products we received had any damage they will be sent back to you immediately\n"
       		+ "Your amount will be further communicated by the Admin";
       
       Properties prop = new Properties();
       prop.put("mail.smtp.host", "smtp.gmail.com");
       prop.put("mail.smtp.port", "587");
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.starttls.enable", "true"); //TLS

       Session session = Session.getInstance(prop,
               new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

       try {

           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress( "digiclothing2020@gmail.com"));
           message.setRecipients(
                   Message.RecipientType.TO,
                   InternetAddress.parse(customer.getEmail())
           );
           message.setSubject("Confirmation mail ");
           message.setText(sendMessage);

           Transport.send(message);

           System.out.println("Done");

       } catch (MessagingException e) {
    	   
    	   throw new RuntimeException(e);
       }
   }

}