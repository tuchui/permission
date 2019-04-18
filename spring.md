##### 一 spring中 HttpMessageConverter

###### 1 requestMappingHandlerAdapter 回默认注册多个转换器

- @RequestBody @ResponseBody

- HttpEntity  ResponseEntity

###### 2 处理 XML 和 JSON

XML  MarshallingHttpMessageConverter

JSON MappingJackson2HttpMessageConverter 

注意 ： 若使用 < mvc:annotation-driven/ >  默认已经配置好了

[spring 配置json转化器](https://blog.csdn.net/eson_15/article/details/51742864)

###### 3 < mvc:annotation-driven > 注解意义

用来代替手动配置

会自动注册 DefaultAnnotationHandlerMapping 和 AnnotationMethodHandlerAdapter 两个bean

同时提供如下服务：

数据绑定支持， 读写Xml支持 ，读写Json支持(jackson), @NumberFormatannotaion 支持 ，

@DateTimeFormat支持

[参考 主要看这个](https://my.oschina.net/abian/blog/128028)

[参考1](https://www.cnblogs.com/dreamroute/p/3890883.html)

[参考2](https://blog.csdn.net/sxbjffsg163/article/details/9955511)

##### 4  **< context:annotation-config />**   < context:component-scan />  <mvc:annotation-driven /> 三种区别

< context:annotation-config />  能够使用 @autowired @Resource @Required等注解

​							（内部注册了 AutowireAnnotationBeanPostProcessor 和 							CommonAnnotationBeanPostProcesso）

< context:component-scan />  不但对类包进行扫描已实施注释驱动Bean定义功能

​						      同时还启用了注释驱动自动注入功能 

​						   （隐式内部注册了 AutowireAnnotationBeanPostProcessor 和 							CommonAnnotationBeanPostProcessor）

​							就是说 使用了 < context:component-scan /> 就可以不使用

​							< context:annotation-config />

< mvc:annotation-driven />     会自动注册 DefaultAnnotationHandlerMapping 和 							AnnotationMethodHandlerAdapter 两个bean

##### 5 使用< mvc :annotation-driven /> 后 自定义转换器

[参考1](https://blog.csdn.net/cslucifer/article/details/78610515)

###### 6  配置 MappingJackson2JsonView

[参考·重要](https://howtodoinjava.com/spring-restful/spring-rest-hello-world-json-example/)

7  ContentNegotiatingViewResolver 内容协商解析器

[参考2](https://www.bbsmax.com/A/n2d9X3V45D/)

