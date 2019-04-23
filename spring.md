#### 一 Spring 配置

#####  spring中 HttpMessageConverter

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



##### Spring配置日志

###### 1 使用SL4J

配置logback.xml文件

-------

#### 二 spring 常用工具类

##### 1 JsonData类

- 添加 ret msg obj

- 添加 toMap转换方法

  ```java
      private boolean ret;
      private String msg;
      private Object obj;
      public JsonData(boolean ret){
          this.ret=ret;
      }
  
      public static JsonData success(String msg){
          JsonData jsonData=new JsonData(true);
          jsonData.msg=msg;
          return jsonData;
      }
      public static JsonData success(String msg,Object obj){
          JsonData jsonData=new JsonData(true);
          jsonData.msg=msg;
          jsonData.obj=obj;
          return jsonData;
      }
      public static JsonData success(){
          JsonData jsonData=new JsonData(true);
          return jsonData;
      }
      public  static JsonData fail(String msg){
          JsonData jsonData=new JsonData(false);
          jsonData.msg=msg;
          return jsonData;
      }
  
      public  Map<String,Object> toMap(){
          HashMap<String,Object> map=new HashMap<>();
          map.put("msg",msg);
          map.put("ret",ret);
          map.put("obj",obj);
          return map;
      }
  ```

  

##### 2 Spring 自定义异常处理

定义参数异常 和 权限异常

PermissionException ParamsException

spring-servlet.xml 配置

注意 ：包括多个视图解析器 InternalViewReslover BeanNameViewReslover

**重点： 视图解析器在xml放置的位置顺序很重要，要放在 internalViewReslover**前面

```xml
<!--json view -->
<bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />

<!--beannameReslover 使用Bean名字来解析视图-->
  <bean class="org.springframework.web.servlet.view.BeanNameViewResolver"/>

  <!--自定义异常解析器-->
    <bean class="com.mao.common.SpringExceptionReslover"/>
```

[参考资料1](https://blog.csdn.net/tony308001970/article/details/72770701)

[参考资料：spring官方文档](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-viewresolver)

##### 3 配置ApplicationContextAware 

作用：外部使用ApplicationContext

```java
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext=applicationContext;
    }

    public static <T> T popBean(Class<T> bean){
        if(applicationContext==null){
            return null;
        }
        return applicationContext.getBean(bean);
    }
    public static <T> T popBean(String className,Class<T> clazz){
        if(applicationContext==null){
            return null;
        }

        return applicationContext.getBean(className,clazz);

    }

```

##### 4  JsonMapper工具类 

作用：将String转换成obj 获奖obj转换string

依赖：

```xml
   <!-- jackson -->
    <dependency>
      <groupId>org.codehaus.jackson</groupId>
      <artifactId>jackson-core-asl</artifactId>
      <version>1.9.13</version>
    </dependency>
    <dependency>
      <groupId>org.codehaus.jackson</groupId>
      <artifactId>jackson-mapper-asl</artifactId>
      <version>1.9.13</version>
    </dependency>
```



```java
@Slf4j
public class JsonMapper {
    private static ObjectMapper objectMapper=new ObjectMapper();
    static {
        // config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }
    public static <T> String obj2String(T src){
        if (src==null){
            return null;
        }
        try {
            return src instanceof String ? (String) src: objectMapper.writeValueAsString(src);
        } catch (IOException e) {
            log.error("parse object to string exception {}",e);
            return null;
        }
    }
    public static <T> T string2Obj(String src, TypeReference<T> typeReference){
        if(src==null || typeReference==null){
            return null;
        }
        try {
            return typeReference.getType().equals(String.class)?(T) src:objectMapper.readValue(src,typeReference);
        } catch (IOException e) {
            log.error("parse String to Obj Exception {}",e);
            return null;
        }
    }
}

```

##### 5 自定义BeanValidator

使用相关jar包

```xml
    <!-- validator -->
    <dependency>
      <groupId>javax.validation</groupId>
      <artifactId>validation-api</artifactId>
      <version>1.1.0.Final</version>
    </dependency>
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-validator</artifactId>
      <version>5.2.4.Final</version>
    </dependency>

	
```

BeanValidator 自定义类

group？ 使用google的guva 需要后期深入了解

```java
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mao.exception.ParamsException;
import org.apache.commons.collections.MapUtils;
import javax.validation.*;
import java.util.*;
public class BeanValidator {
    private static ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
    public static <T>Map<String,String> validate(T clazz,Class... groups){
        //1生成valdator实例
        //2 进行校验,返回校验结果
        //3 判断校验结果是否为null
        //3.1 为null 返回空Collections
        //3.2 不为null 把集合存到map中
        Validator validator=validatorFactory.getValidator();
        Set valRes=validator.validate(clazz,groups);
        if(valRes.isEmpty()){
            return Collections.emptyMap();
        }else{
            Iterator iterator=valRes.iterator();
            LinkedHashMap<String,String> error= Maps.newLinkedHashMap();
            while (iterator.hasNext()){
                ConstraintViolation constraintViolation=(ConstraintViolation)iterator.next();
                error.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
            }
            return error;
        }
    }
    public static Map<String,String> validateList(Collection<?> collections){
        Preconditions.checkNotNull(collections);
        Map error;
        Iterator iterator=collections.iterator();
        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object obj=iterator.next();
            error=validate(obj,new Class[0]);
        }while (error.isEmpty());
        return error;
    }
    
    public static Map<String,String> validateObject(Object obj,Object... objects){
        if(obj!=null && objects.length>0){
            return validateList(Lists.asList(obj,objects));
        }else {
            return validate(obj,new Class[0]);
        }
    }

    public static void check(Object params){
     Map<String,String> results= BeanValidator.validate(params);
     if(MapUtils.isNotEmpty(results)){
         throw new ParamsException(results.toString());
     }
    }
}

```

##### 6 自定义HttpInterceptor

作用： 打印url传来的参数 处理请求的时间

注意：需要 在xml添加自定义拦截器

​		前置处理 prehandle 返回 true 才能继续进行

​		后置处理 postHandle 在dispatcherServlet进行视图渲染之前

​		afterCompletion 在dispatcherServlet进行视图渲染之后 ，最后资源的清理

```xml
  <!--自定义httpInterceptor-->
    <mvc:interceptors>
        <bean class="com.mao.common.HttpInterceptor"/>
    </mvc:interceptors>
```

```java
import com.mao.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    private static final String START_TIME="REQUEST_START_TIME";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        Map parameterMap=request.getParameterMap();
        log.info("preHandle: request Url:{},request parameters {}",url, JsonMapper.obj2String(parameterMap));
        Long time=System.currentTimeMillis();
        request.setAttribute(START_TIME,time);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url=request.getRequestURI();
        Long StartTime=(Long)request.getAttribute(START_TIME);
        Long EndTime=System.currentTimeMillis();
        Long CostTime=EndTime-StartTime;
        log.info("afterComplection url:{} cost time:{} ",url,CostTime);
    }
}

```

#### 三 权限系统 部门相关管理

##### 1  新增部门接口

编写 DeptParam  

编写 SysDeptController

编写 SysDeptService 

Builder的使用 建造者 @Builder注解

void save(DeptParam param);

private boolean checkExist(Integer parentId,String deptName ,Integer deptId);

private String getLevel(Integer id)

编写 LevleUtil

##### 2  部门层级树开发

编写 DeptDTO

编写 SysTreeService  

递归算法 层级遍历 deptTree()  deptListToTree()  transformDeptTree

MultiMap  等价于 Map<String ,List< Object> >

增加SysDeptMapper  geAllDept() 方法

SysDeptController 增加 tree.json

