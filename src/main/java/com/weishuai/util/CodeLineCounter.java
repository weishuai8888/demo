package com.weishuai.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * @Description Java项目代码行数统计工具类
 * @Author ws
 * @Date 2025/2/25 10:14
 */
public class CodeLineCounter {
    // 需要统计的文件扩展名
    private static final Set<String> TARGET_EXTENSIONS = new HashSet<>(
            Arrays.asList(".java", ".mapper", ".yml")
    );

    // 统计结果存储
    private static class CountResult {
        int totalLines = 0;
        int fileCount = 0;
        Map<String, Integer> extensionLines = new HashMap<>();
        Map<String, Integer> extensionFiles = new HashMap<>();
    }

    /**
     * 统计指定目录下的代码行数
     * @param projectPath 项目根目录路径
     * @return 统计结果字符串
     */
    public static String countProjectLines(String projectPath) throws IOException {
        CountResult result = new CountResult();
        
        Files.walkFileTree(Paths.get(projectPath), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                String fileName = file.toString();
                String extension = getFileExtension(fileName);
                
                if (TARGET_EXTENSIONS.contains(extension)) {
                    try {
                        int lines = countFileLines(file);
                        result.totalLines += lines;
                        result.fileCount++;
                        
                        // 按扩展名统计
                        result.extensionLines.merge(extension, lines, Integer::sum);
                        result.extensionFiles.merge(extension, 1, Integer::sum);
                    } catch (IOException e) {
                        System.err.println("Error counting lines in file: " + fileName);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        
        return generateReport(result);
    }

    /**
     * 统计单个文件的行数
     */
    private static int countFileLines(Path file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        }
    }

    /**
     * 获取文件扩展名
     */
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex) : "";
    }

    /**
     * 生成统计报告
     */
    private static String generateReport(CountResult result) {
        StringBuilder report = new StringBuilder();
        report.append("\n=== 代码行数统计报告 ===\n");
        report.append("总文件数: ").append(result.fileCount).append("\n");
        report.append("总行数: ").append(result.totalLines).append("\n\n");
        
        report.append("按文件类型统计:\n");
        for (String extension : TARGET_EXTENSIONS) {
            int files = result.extensionFiles.getOrDefault(extension, 0);
            int lines = result.extensionLines.getOrDefault(extension, 0);
            report.append(String.format("%s 文件: %d个, %d行\n", 
                extension, files, lines));
        }
        
        return report.toString();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        try {
            String projectPath = "/opt/yunding/northwest-park/northwest-park-service";  // 替换为实际项目路径
            String report = countProjectLines(projectPath);
            System.out.println(report);
        } catch (IOException e) {
            System.err.println("统计失败: " + e.getMessage());
        }
    }
}
