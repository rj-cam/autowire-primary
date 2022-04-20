package com.example.autowireprimary;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.example2.SomeHostDASImpl2;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class AutowirePrimaryApplicationWorkingTests {

   @Autowired
   ApplicationContext ctx;

   @Test
   void working() {
      // Register a new SomeHostDAS implementation bean and set as primary
      BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(SomeHostDASImpl2.class).setPrimary(true).setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE).getBeanDefinition();
      BeanDefinitionRegistry.class.cast(ctx).registerBeanDefinition("someHostDASImpl2", beanDefinition);

      log.info("[working] After Registering New Bean Definition ----------------------------------------------------------------------------------------\n{}", beanDefinition);
      SomeHostDAS hostBean = ctx.getBean(SomeHostDAS.class);
      assertInstanceOf(SomeHostDASImpl2.class, hostBean);
      SomeBusinessService businessBean = ctx.getBean(SomeBusinessService.class);
      log.info("[working] hostBean = {}, businessBean = {}", hostBean, businessBean);
      businessBean.execute();
      assertInstanceOf(SomeHostDASImpl2.class, businessBean.getHostDAS());
   }

}
