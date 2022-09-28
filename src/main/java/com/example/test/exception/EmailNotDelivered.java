package com.example.test.exception;

public class EmailNotDelivered extends RuntimeException{
   public EmailNotDelivered (String message){
       super(message);
   }
}
