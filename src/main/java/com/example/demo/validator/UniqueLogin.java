package com.example.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
//使える場所の指定（メソッド、フィールド）
@Target({ElementType.METHOD, ElementType.FIELD})
//保持期間の指定（RetentionPolicy.RUNTIMEは起動中はいつでも使える）
@Retention(RetentionPolicy.RUNTIME)
//チェック対象のクラスを指定
@Constraint(validatedBy = UniqueLoginValidator.class)
//@UniqueLoginというアノテーションが使えるようになる
public @interface UniqueLogin {
    String message() default "このユーザ名は既に登録されています";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
