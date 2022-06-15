package com.wzp109761.annotation_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.wzp109761.annotation.BindPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * 注解处理器 去代码中找到专门用来标记Activity的类，得到他所标记的类,生成ActivityUtil类
 */

//@SupportedAnnotationTypes({"com.wzp109761.annotation.BindPath"})
@AutoService(Processor.class)  //标记注解处理器
public class AnnotationCompiler extends AbstractProcessor {

    //生成Java 文件的工具
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer=processingEnv.getFiler();
    }

    /**
     * 声明支持的java版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 声明你这个注解处理器要找的注解是谁,同 //@SupportedAnnotationTypes({"com.wzp109761.annotation.BindPath"})
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types=new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 核心方法，找程序中标记了的内容，都在这个方法中
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //得到模块中标记了BindPath的注解的内容
        Set<? extends Element> elementsAnnotatedWith= roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //类节点：TypeElement
        //包节点：PackageElement
        //成员变量节点：VariableElement
        //方法节点：ExecutableElement
        Map<String,String> map=new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement=(TypeElement) element;
            String key=typeElement.getAnnotation(BindPath.class).value();
            String activityName = typeElement.getQualifiedName().toString();
            map.put(key,activityName+".class");
        }
        //生成文件
        if(map.size()>0){
            createClass(map);
        }
        return false;
    }

    private void createClass(Map<String,String> map) {
        MethodSpec.Builder methodBuilder=MethodSpec.methodBuilder("putActivity")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class);
        Iterator<String> iterator=map.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String activityName=map.get(key);

            methodBuilder.addStatement("com.wzp109761.router.ARouter.getInstance().addActivity(\""+key+"\","+activityName+")");
        }
        MethodSpec methodSpec=methodBuilder.build();
        //获取接口类
        ClassName iRouter=ClassName.get("com.wzp109761.router","IRouter");

        TypeSpec typeSpec=TypeSpec.classBuilder("ActivityUtil"+System.currentTimeMillis())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(iRouter)
                .addMethod(methodSpec)
                .build();

        //构建目录对象
        JavaFile javaFile=JavaFile.builder("com.wzp109761.util",typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}