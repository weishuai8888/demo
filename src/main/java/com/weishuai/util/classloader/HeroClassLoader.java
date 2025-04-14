package com.weishuai.util.classloader;

import org.apache.commons.lang3.ObjectUtils;

import java.io.*;

public class HeroClassLoader extends ClassLoader {

    private String classpath;

    public HeroClassLoader(String classpath) {
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //输入流，通过类的全限定名加载文件到字节数组
        try {
            byte[] classDate = new byte[0];
            classDate = getData(name);
            if (ObjectUtils.isNotEmpty(classDate)) {
                //将字节数组数据转化为字节码对象
                return defineClass(name, classDate, 0, classDate.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private byte[] getData(String className) throws IOException {
        String path = classpath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        try(InputStream in = new FileInputStream(path);
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        }catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        return null;
    }
}

