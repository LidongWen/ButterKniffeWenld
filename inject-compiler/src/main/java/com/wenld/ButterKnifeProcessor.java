package com.wenld;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ButterKnifeProcessor extends AbstractProcessor {
    /**
     * 处理Element工具类
     */
    private Elements elementUtils;
    /**
     * 生成java文件
     */
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    //处理注解
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
//        Map<TypeElement, BindingSet> bindingMap = findAndParseTargets(env);
//
//
        Map<TypeElement, List<FieldBinding>> map = new HashMap<>();

        for (Element element : env.getElementsAnnotatedWith(BindView.class)) {
            //获取 Activity
            TypeElement activityElement = (TypeElement) element.getEnclosingElement();
            List<FieldBinding> list = map.get(activityElement);
            if (list == null) {
                list = new ArrayList<>();
                map.put(activityElement, list);
            }
            //获取 id
            int id = element.getAnnotation(BindView.class).value();
            //获取名字
            String fileName = element.getSimpleName().toString();
            //获取类型
            TypeMirror typeMirror = element.asType();
            FieldBinding fieldBinding = new FieldBinding(fileName, typeMirror, id);
        }

        for (Map.Entry<TypeElement, List<FieldBinding>> entry :
                map.entrySet())
        {
            TypeElement activityElement=entry.getKey();
            List<FieldBinding> list=entry.getValue();
            String packageName =elementUtils.getPackageOf(activityElement).getQualifiedName().toString();
            String activityName=activityElement.getSimpleName().toString();
//            ClassName viewBuild=ClassName.get
        }
        return false;
    }


    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(BindView.class);

        return annotations;
    }
}
