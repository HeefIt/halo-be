package com.heef.halo.domain.AOpenAiPractice.openAiService.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * 知识库服务实现类
 *
 * @author kyrie.huang
 * @date 2026/3/2 14:21
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KnowledgeBaseServiceImpl {

    private final VectorStore vectorStore;

    //根目录下的knowledge-base/下所有文件
    @Value("${halo.knowledge-base.path}")
    private String knowledgeBasePath;

    /**
     * 初始化知识库：读取文档 → 分割 → 向量化 → 存储
     */
    @PostConstruct
    public void initKnowledgeBase() throws IOException {
        Path dir = Paths.get(knowledgeBasePath);
        if (!Files.exists(dir)) {
            return;
        }

        // 1. 读取所有文档文件
        List<Document> documents = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(dir)) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> documents.addAll(loadDocument(file)));
        }

        // 2. 使用 TokenTextSplitter 进行智能分割
        TokenTextSplitter splitter = new TokenTextSplitter(500, 100, 5, 1000, true);
        List<Document> splitDocs = splitter.apply(documents);

        // 3. 添加元数据（来源文件名、分割索引等）
        for (int i = 0; i < splitDocs.size(); i++) {
            Document doc = splitDocs.get(i);
            doc.getMetadata().put("chunk_index", i);
            doc.getMetadata().put("total_chunks", splitDocs.size());
        }

        // 4. 存储到向量数据库
        vectorStore.add(splitDocs);
        log.info("知识库初始化完成，共加载 {} 个文档片段", splitDocs.size());
    }

    /**
     * 使用 TikaDocumentReader 读取各种格式文档
     */
    private List<Document> loadDocument(Path filePath) {
        try {
            FileSystemResource resource = new FileSystemResource(filePath.toFile());
            TikaDocumentReader reader = new TikaDocumentReader(resource);

            List<Document> docs = reader.read();
            docs.forEach(doc ->
                    doc.getMetadata().put("source", filePath.getFileName().toString()));

            return docs;
        } catch (Exception e) {
            log.error("读取文档失败: {}", filePath, e);
            return Collections.emptyList();
        }
    }

    /**
     * 动态添加新文档到知识库
     */
    public void addDocument(MultipartFile file) throws IOException {
        InputStreamResource resource = new InputStreamResource(file.getInputStream());
        TikaDocumentReader reader = new TikaDocumentReader(resource);

        List<Document> docs = reader.read();
        docs.forEach(doc ->
                doc.getMetadata().put("source", file.getOriginalFilename()));

        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> splitDocs = splitter.apply(docs);
        vectorStore.add(splitDocs);
    }
}