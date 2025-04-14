package com.weishuai.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description 统计JAVA工程的代码行数
 * @Author ws
 * @Date 2024/9/2 13:38
 */
public class JavaProjectLineCounter {

    public static void main(String[] args) {
        String projectPath = "/opt/yunding/northwest-park/northwest-park-service"; // 替换为你的项目路径
//        String projectPath = "/opt/yunding/northwest-park/northwest-park-service/sheno-modules/sheno-server"; // 替换为你的项目路径

        try {
            int totalLines = countJavaCodeLines(projectPath);
            System.out.println("Total code lines in the project: " + totalLines);
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    /**
     * 计算 Java 工程中的代码行数。
     *
     * @param projectPath 项目的根目录路径
     * @return 代码行总数
     * @throws IOException 如果读取文件时发生错误
     */
    public static int countJavaCodeLines(String projectPath) throws IOException {
        File rootDirectory = new File(projectPath);
        int totalLines = 0;

        if (rootDirectory.isDirectory()) {
            File[] files = rootDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        totalLines += countJavaCodeLines(file.getAbsolutePath()); // 递归处理子目录
                    } else if (file.getName().endsWith(".java") || file.getName().endsWith(".xml")) {
                        totalLines += countCodeLinesInFile(file);
                    }
                }
            }
        }
        return totalLines;
    }

    /**
     * 计算单个 Java 文件中的代码行数。
     *
     * @param file Java 文件
     * @return 代码行数
     * @throws IOException 如果读取文件时发生错误
     */
    public static int countCodeLinesInFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int lineCount = 0;
            String line;
            boolean inComment = false; // 是否处于多行注释中

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // 删除行首尾的空白字符

                // 处理多行注释
                if (!inComment && line.contains("/*")) {
                    inComment = true;
                }
                if (inComment && line.contains("*/")) {
                    inComment = false;
                }

                // 忽略注释行和空白行
                if (!inComment && !line.startsWith("//") && line.length() > 0) {
                    lineCount++;
                }
            }

            return lineCount;
        }
    }
}
