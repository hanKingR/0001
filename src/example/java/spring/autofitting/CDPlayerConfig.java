package spring.autofitting;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//通过 java 配置来装配
@Configuration
@ComponentScan
//@ComponentScan("XXXX")  指定包名
//@ComponentScan(basePackages="XXXX")  指定基础包名
//@ComponentScan(basePackages = {"XXX","CC"}) 指定多个基础包名
//@ComponentScan(basePackageClasses= {CDPlayer.class,DVDPlauer.class}) 这些类所在的包会成为扫描的基础包
public class CDPlayerConfig {

}
