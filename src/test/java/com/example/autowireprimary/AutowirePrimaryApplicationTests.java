package com.example.autowireprimary;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.example1.SomeHostDASImpl1;
import com.example2.SomeHostDASImpl2;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class AutowirePrimaryApplicationTests {

   @Autowired
   ApplicationContext ctx;

   @Test
   void notWorking() {
      log.info("[notWorking] Initial Configuration ---------------------------------------------------------------------------------------------------------");
      SomeHostDAS hostBean1 = ctx.getBean(SomeHostDAS.class);
      assertInstanceOf(SomeHostDASImpl1.class, hostBean1);
      SomeBusinessService businessBean1 = ctx.getBean(SomeBusinessService.class);
      log.info("[notWorking] hostBean1 = {}, businessBean1 = {}", hostBean1, businessBean1);
      businessBean1.execute();
      assertInstanceOf(SomeHostDASImpl1.class, businessBean1.getHostDAS());

      // Register a new SomeHostDAS implementation bean and set as primary
      BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(SomeHostDASImpl2.class).setPrimary(true).setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE).getBeanDefinition();
      BeanDefinitionRegistry.class.cast(ctx).registerBeanDefinition("someHostDASImpl2", beanDefinition);

      log.info("[notWorking] After Registering New Bean Definition ----------------------------------------------------------------------------------------\n{}", beanDefinition);
      SomeHostDAS hostBean2 = ctx.getBean(SomeHostDAS.class);
      assertInstanceOf(SomeHostDASImpl2.class, hostBean2);
      assertNotSame(hostBean1, hostBean2);
      SomeBusinessService businessBean2 = ctx.getBean(SomeBusinessService.class);
      log.info("[notWorking] hostBean2 = {}, businessBean2 = {}", hostBean2, businessBean2);
      businessBean1.execute();
      assertNotSame(businessBean1, businessBean2);
      assertInstanceOf(SomeHostDASImpl2.class, businessBean2.getHostDAS());
   }

}
