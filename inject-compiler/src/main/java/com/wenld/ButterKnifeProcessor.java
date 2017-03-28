package com.wenld;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.MethodSpec.methodBuilder;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ButterKnifeProcessor extends AbstractProcessor {
    /**
     *
     */
    private Elements elementUtils;
    /**
     *
     */
    private Filer filer;
    private Messager messager;
    @Override
    public synchronized void init(ProcessingEnvironment env) {

        super.init(env);
        print("init");
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
        messager = env.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        print("getSupportedAnnotationTypes");
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    //process annotion
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        print("process");
//        Map<TypeElement, List<FieldBinding>> map = new HashMap<>();

//        for (Element element : env.getElementsAnnotatedWith(BindView.class)) {
//            //get the Activity
//            TypeElement activityElement = (TypeElement) element.getEnclosingElement();
//            List<FieldBinding> list = map.get(activityElement);
//            if (list == null) {
//                list = new ArrayList<>();
//                map.put(activityElement, list);
//            }
//            //get  id
//            int id = element.getAnnotation(BindView.class).value();
//            //get fieldName
//            String fieldName = element.getSimpleName().toString();
//            //get mirror
//            TypeMirror typeMirror = element.asType();
//            FieldBinding fieldBinding = new FieldBinding(fieldName, typeMirror, id);
//            list.add(fieldBinding);
//        }
//
//        for (Map.Entry<TypeElement, List<FieldBinding>> item :
//                map.entrySet()) {
//            TypeElement activityElement = item.getKey();
//
//            //get packageName
//            String packageName = elementUtils.getPackageOf(activityElement).getQualifiedName().toString();
//            //get  activityName
//            String activityName = activityElement.getSimpleName().toString();
//
//            //transfrom type Activity with system can discern
//            ClassName activityClassName = ClassName.bestGuess(activityName);
//            ClassName viewBuild = ClassName.get("T", activityName);    //
//
//            TypeSpec.Builder result = TypeSpec.classBuilder(activityClassName + "$$ViewBinder")
//                    .addModifiers(Modifier.PUBLIC)
//                    .addTypeVariable(TypeVariableName.get("T", activityClassName))
//                    .addSuperinterface(ParameterizedTypeName.get(viewBuild, activityClassName));
//
//            MethodSpec.Builder method = methodBuilder("bind")      //methodName
//                    .addModifiers(Modifier.PUBLIC)                          // modifier
//                    .returns(TypeName.VOID)
//                    .addAnnotation(Override.class)
//                    .addParameter(activityClassName, "target", Modifier.FINAL);
//
//            List<FieldBinding> list = item.getValue();
//            for (FieldBinding fieldBinding : list) {
//                //
//                String pacageName = fieldBinding.getType().toString();
//                ClassName viewClass = ClassName.bestGuess(pacageName);
//
//                method.addStatement("target.$L=($T)target.findViewById($L)", fieldBinding.getName(), viewClass, fieldBinding.getResId());
//
//            }
//
//            result.addMethod(method.build());
//
//            try {
//                JavaFile.builder(packageName, result.build()).build().writeTo(filer);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }




        MethodSpec fore = methodBuilder("fore")
                .returns(int.class)
                .addStatement("int result = 0")
                .addStatement("$T() date = new $T()", Date.class, Date.class)
                .beginControlFlow("for (int i = $L; i < $L; i++)", 0, 10)
                .addStatement("result = result $L i", "+")
                .endControlFlow()
                .addStatement("return result +  $S", "test")
                .build();



        TypeSpec hello = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(fore)
                .build();


        String packgeName = "wenld.github.butterkniffewenld";

        try {
            JavaFile file = JavaFile.builder(packgeName, hello).build();
            file.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        print("getSupportedAnnotations");
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(BindView.class);

        return annotations;
    }

    private void print(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

}
