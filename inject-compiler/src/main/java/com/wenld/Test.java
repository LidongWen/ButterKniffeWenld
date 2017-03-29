package com.wenld;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Date;

import javax.lang.model.element.Modifier;

import static com.squareup.javapoet.MethodSpec.methodBuilder;

/**
 * <p/>
 * Author: wenld on 2017/3/28 11:46.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class Test {
    @BindView(1)
    String aa;
    public static void main(String[] args) throws IOException {
        MethodSpec main =
                methodBuilder("main")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, "hello world")
                        .build();


        MethodSpec fore = MethodSpec.methodBuilder("fore")
                .returns(int.class)
                .addStatement("int result = 0")
                .addStatement("$T() date = new $T()", Date.class, Date.class)
                .beginControlFlow("for (int i = $L; i < $L; i++)", 0, 10)
                .addStatement("result = result $L i", "+")
                .endControlFlow()
                .addStatement("return result +  $S", "test")
                .build();


        ClassName hoverboard = ClassName.get("com.mattel", "Hoverboard");
        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listOfHoverboards = ParameterizedTypeName.get(list, hoverboard);

        MethodSpec beyond = MethodSpec.methodBuilder("beyond")
                .returns(listOfHoverboards)
                .addStatement("$T result = new $T<>()", listOfHoverboards, arrayList)
                .addStatement("result.add(new $T())", hoverboard)
                .addStatement("result.add(new $T())", hoverboard)
                .addStatement("result.add(new $T())", hoverboard)
                .addStatement("return result")
                .build();


        TypeSpec hello = TypeSpec.classBuilder("HelloWorld") //添加类的名称
                .addModifiers(Modifier.PUBLIC) //添加修饰的关键字
                .addMethod(main)  //添加该类中的方法
                .addMethod(fore)
                .addMethod(beyond)
                .build();


        String packgeName = "com.zs.javapoet"; //生成类的包名
        JavaFile file = JavaFile.builder(packgeName, hello).build(); //在控制台输出
        file.writeTo(System.out);
    }
}
