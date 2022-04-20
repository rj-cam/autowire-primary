package com.example.autowireprimary;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class SomeBusinessService {

   @Autowired(required=true) 
   private SomeHostDAS hostDAS;

   public SomeBusinessService() {
      super();
      log.info("[constructor] Instantiating the class instance. hostDAS = {}, this = {}", hostDAS, this);
   }

   public void execute() {
      log.info("[execute] Performing some business logic with the host. hostDAS = {}", hostDAS);
      try {
         hostDAS.api1("SomeBusinessService");
         hostDAS.api2("SomeBusinessService");
      } catch (Exception e) {
         log.error("[execute] Exception: {}", e);
      }
   }
   
   @PostConstruct
   public void postConstruct() {
      log.info("[postConstruct] Post intialization of the class instance. hostDAS = {}", hostDAS);      
   }
   
   static {
      log.info("[init] Initializing the class.");
   }
   
}
