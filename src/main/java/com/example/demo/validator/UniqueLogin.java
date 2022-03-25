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
	//以下3つ要素は全て必要
    String message() default "このユーザ名は既に登録されています";
    //特定のバリデーショングループがカスタマイズできるような設定です。
    //空の Class<?> 型で初期化されている必要があります。
    Class<?>[] groups() default{};
    //チェック対象のオブジェクトになんらかのメタ情報を与えるためだけの宣言
    Class<? extends Payload>[] payload() default{};
}
