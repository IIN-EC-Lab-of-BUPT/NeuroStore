package bupt.embemc.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luozhanfeng
 * @version 1.0
 * @date 2022/7/5 16:47
 * @description 配置 Druid 监控管理后台
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }


    /**
     * 内置 Servlet 容器时没有web.xml文件，所以使用 Spring Boot 的注册 Servlet 方式
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        /* 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到 */

        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "root");
        /* 默认就是允许所有访问 */
        initParams.put("allow", "");

        bean.setInitParameters(initParams);
        return bean;
    }


    /**
     * 2、配置一个web监控的filter
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }

}