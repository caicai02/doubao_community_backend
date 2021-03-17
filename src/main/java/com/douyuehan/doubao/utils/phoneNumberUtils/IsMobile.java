package com.douyuehan.doubao.utils.phoneNumberUtils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * TYPE——接口、类、枚举、注解
 * FIELD——字段、枚举的常量
 * METHOD——方法
 * PARAMETER——方法参数
 * CONSTRUCTOR ——构造函数
 * LOCAL_VARIABLE——局部变量
 * ANNOTATION_TYPE——注解
 * PACKAGE——包
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
/**
 *  RUNTIME表示被标注的注解最终保存在class文件中,并可以被反射机制读取;
 *  SOURCE表示被标注的注解最终保存在java源文件中;
 *  CLASS表示被标注的注解最终保存在class文件中
 */
@Retention(RUNTIME)
//这个注解只是用来标注生成javadoc的时候是否会被记录。
@Documented
//自定义验证注解
@Constraint(validatedBy = {IsMobileValidator.class })
public @interface  IsMobile {
	
	boolean required() default true;
	
	String message() default "手机号码格式错误";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
