package com.example1;

import org.springframework.stereotype.Service;

import com.example.autowireprimary.SomeHostDAS;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SomeHostDASImpl1 implements SomeHostDAS {

   @Override
   public String api1(String input) {
      log.debug("[api1] input = {}", input);
      return input != null ? "impl1.api1:" + input.toLowerCase() : null;
   }

   @Override
   public String api2(String input) {
      log.debug("[api2] input = {}", input);
      return input != null ? "impl1.api2:" + input.toUpperCase() : null;
   }

}
