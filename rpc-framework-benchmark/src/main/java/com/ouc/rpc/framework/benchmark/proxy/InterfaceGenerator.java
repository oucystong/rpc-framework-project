package com.ouc.rpc.framework.benchmark.proxy;

import javax.tools.*;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class InterfaceGenerator {
    public static void generate(Integer interfaceNameSeq) throws ClassNotFoundException, IOException, URISyntaxException {

//        System.out.println("generate " + interfaceNameSeq + "'th interface class file");

        // Load the class for which we want to generate interfaces
        Class<?> targetClass = Class.forName("com.ouc.rpc.framework.benchmark.proxy.Service");

        // Get all the methods in the class
        Method[] methods = targetClass.getMethods();

        // Create a new interface class
        String interfaceName = "TestService" + interfaceNameSeq;
        String packageName = "com.ouc.rpc.framework.benchmark.proxy.inter";
        StringBuilder sb = new StringBuilder();
        sb.append("package " + packageName + ";\n\n");
        sb.append("public interface " + interfaceName + " {\n");

        // Define all the methods in the interface
        String returnType = methods[0].getReturnType().getName();
        String methodName = methods[0].getName();
        Class<?>[] parameterTypes = methods[0].getParameterTypes();
        String parameterList = "";
        for (Class<?> parameterType : parameterTypes) {
            String parameterTypeName = parameterType.getName();
            parameterList += parameterTypeName + " " + "arg" + ", ";
        }
        if (parameterList.endsWith(", ")) {
            parameterList = parameterList.substring(0, parameterList.length() - 2);
        }
        sb.append("\n  public " + returnType + " " + methodName + "(" + parameterList + ");");


        // Close the interface
        sb.append("\n}");

        // Compile the interface and save it to a file
        String interfaceSource = sb.toString();
        String fileName = "TestService" + interfaceNameSeq + ".java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        JavaFileObject javaFile = new SimpleJavaFileObject(new URI(fileName), JavaFileObject.Kind.SOURCE) {
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return interfaceSource;
            }
        };
        Iterable<? extends JavaFileObject> sourceFiles = Arrays.asList(javaFile);
        Iterable<String> options = Arrays.asList("-d", "/Users/ethan/Desktop/2023/code/rpc-framework-project/rpc-framework-benchmark/src/main/java/");
        compiler.getTask(null, fileManager, null, options, null, sourceFiles).call();
        fileManager.close();
//        System.out.println("interface generated successfully!");
    }


}

