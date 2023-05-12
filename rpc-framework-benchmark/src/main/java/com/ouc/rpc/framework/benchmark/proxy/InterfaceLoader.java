package com.ouc.rpc.framework.benchmark.proxy;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class InterfaceLoader {

    public static ArrayList<Class> getClass(String classFileDirectoryPath) throws IOException, ClassNotFoundException {
        ArrayList<Class> classes = new ArrayList<>();

        // Get all the class files in the directory
        File directory = new File(classFileDirectoryPath);
        File[] classFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }
        });

        // Create a new URLClassLoader with the target directory as the classpath
        File file = new File("/Users/ethan/Desktop/2023/code/rpc-framework-project/rpc-framework-benchmark/src/main/java");
        URL url = file.toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[]{url});

        // Load each class file and print its name
        for (File classFile : classFiles) {
            String className = classFile.getName().replaceAll(".class", "");
            String newClassName = "com.ouc.rpc.framework.benchmark.proxy.inter." + className;
            Class<?> clazz = classLoader.loadClass(newClassName);
            classes.add(clazz);
        }
        return classes;
    }
}
