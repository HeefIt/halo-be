package com.heef.halo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
class HaloStarterApplicationTests {

    @Autowired
    private OpenAiEmbeddingModel openAiEmbeddingModel;

    /**
     * 向量相似度计算工具类
     */
    public static class VectorSimilarityUtil {

        /**
         * 计算欧式距离 (Euclidean Distance)
         * 值越小表示越相似，自身距离为0
         */
        public static double euclideanDistance(float[] vec1, float[] vec2) {
            if (vec1.length != vec2.length) {
                throw new IllegalArgumentException("向量维度不匹配");
            }

            double sum = 0.0;
            for (int i = 0; i < vec1.length; i++) {
                double diff = vec1[i] - vec2[i];
                sum += diff * diff;
            }
            return Math.sqrt(sum);
        }

        /**
         * 计算余弦相似度 (Cosine Similarity)
         * 值越接近1表示越相似，自身相似度为1
         */
        public static double cosineSimilarity(float[] vec1, float[] vec2) {
            if (vec1.length != vec2.length) {
                throw new IllegalArgumentException("向量维度不匹配");
            }

            double dotProduct = 0.0;
            double norm1 = 0.0;
            double norm2 = 0.0;

            for (int i = 0; i < vec1.length; i++) {
                dotProduct += vec1[i] * vec2[i];
                norm1 += Math.pow(vec1[i], 2);
                norm2 += Math.pow(vec2[i], 2);
            }

            // 避免除以0
            if (norm1 == 0 || norm2 == 0) {
                return 0.0;
            }

            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        /**
         * 获取欧式距离的文本评级
         */
        public static String getEuclideanRating(double distance) {
            if (distance < 5) return "【极近】";
            if (distance < 10) return "【较近】";
            if (distance < 15) return "【中等】";
            if (distance < 20) return "【较远】";
            return "【极远】";
        }

        /**
         * 获取余弦相似度的文本评级
         */
        public static String getCosineRating(double similarity) {
            if (similarity > 0.9) return "【极度相似】";
            if (similarity > 0.7) return "【高度相似】";
            if (similarity > 0.5) return "【中度相似】";
            if (similarity > 0.3) return "【低度相似】";
            return "【几乎无关】";
        }
    }

    /**
     * 测试1：简单测试 - 中文字符串的向量化
     */
    @Test
    void testSimpleEmbedding() {
        log.info("========== 测试1：简单向量化测试 ==========");

        String text = "我爱吃苹果,我也爱用苹果";
        float[] embedding = openAiEmbeddingModel.embed(text);

        log.info("原始文本: {}", text);
        log.info("向量维度: {}", embedding.length);
        log.info("向量前10个值: {}", Arrays.toString(Arrays.copyOf(embedding, 10)));
        log.info("向量范数: {}", calculateNorm(embedding));
    }

    /**
     * 测试2：国际冲突主题的相似度对比
     */
    @Test
    void testInternationalConflictSimilarity() {
        log.info("\n========== 测试2：国际冲突主题相似度对比 ==========");

        // 定义测试文本
        String baseText = "国际冲突导致地区局势紧张，多国呼吁通过外交途径解决争端";

        List<String> texts = Arrays.asList(
                baseText,  // 0: 完全相同
                "地区冲突加剧，各国敦促和平谈判解决分歧",                // 1: 高度相似
                "足球世界杯决赛即将开赛，球迷们热情高涨",                // 2: 体育新闻（完全不相关）
                "全球经济面临通胀压力，央行考虑调整利率政策",            // 3: 经济新闻（部分相关？）
                "联合国安理会就地区冲突召开紧急会议，呼吁停火"           // 4: 国际新闻（高度相关）
        );

        // 批量生成向量
        List<float[]> embeddings = texts.stream()
                .map(text -> openAiEmbeddingModel.embed(text))
                .collect(Collectors.toList());

        // 1. 自对比测试
        log.info("\n【场景一：相同文本自对比】");
        float[] baseVector = embeddings.get(0);

        double selfEuclidean = VectorSimilarityUtil.euclideanDistance(baseVector, baseVector);
        double selfCosine = VectorSimilarityUtil.cosineSimilarity(baseVector, baseVector);

        log.info("基准文本: {}", texts.get(0));
        log.info("自对比 - 欧式距离: {} (应为0)", selfEuclidean);
        log.info("自对比 - 余弦相似度: {} (应为1)", selfCosine);

        // 2. 与其他文本对比
        log.info("\n【场景二：基准文本与其他文本对比】");
        log.info("基准文本[0]: {}", texts.get(0));
        log.info("");

        for (int i = 1; i < texts.size(); i++) {
            log.info("对比文本[{}]: {}", i, texts.get(i));

            double euclidean = VectorSimilarityUtil.euclideanDistance(baseVector, embeddings.get(i));
            double cosine = VectorSimilarityUtil.cosineSimilarity(baseVector, embeddings.get(i));

            log.info("  欧式距离: {:.6f} {}", euclidean, VectorSimilarityUtil.getEuclideanRating(euclidean));
            log.info("  余弦相似度: {:.6f} {}", cosine, VectorSimilarityUtil.getCosineRating(cosine));
            log.info("");
        }

        // 3. 相似度排序
        log.info("\n【场景三：相似度排序（从高到低）】");

        List<SimilarityResult> results = IntStream.range(0, texts.size())
                .mapToObj(i -> new SimilarityResult(
                        i,
                        texts.get(i),
                        VectorSimilarityUtil.cosineSimilarity(baseVector, embeddings.get(i))
                ))
                .sorted((a, b) -> Double.compare(b.similarity, a.similarity))
                .collect(Collectors.toList());

        for (SimilarityResult result : results) {
            log.info("  [{}] {}... - 相似度: {:.4f} {}",
                    result.index,
                    result.text.substring(0, Math.min(15, result.text.length())),
                    result.similarity,
                    VectorSimilarityUtil.getCosineRating(result.similarity)
            );
        }
    }

    /**
     * 测试3：多组文本的交叉相似度对比
     */
    @Test
    void testMultiTextSimilarity() {
        log.info("\n========== 测试3：多组文本交叉相似度对比 ==========");

        // 定义多组不同主题的文本
        List<String> texts = Arrays.asList(
                "国际冲突加剧，多国呼吁和平解决",           // 0: 国际政治
                "足球世界杯决赛即将开始",                   // 1: 体育
                "篮球比赛进入加时赛",                       // 2: 体育
                "央行宣布降息以刺激经济",                   // 3: 经济
                "股市迎来开门红",                           // 4: 经济
                "联合国召开紧急会议讨论地区冲突"            // 5: 国际政治
        );

        // 生成所有向量
        List<float[]> embeddings = texts.stream()
                .map(text -> openAiEmbeddingModel.embed(text))
                .collect(Collectors.toList());

        // 创建相似度矩阵
        int size = texts.size();
        double[][] cosineMatrix = new double[size][size];
        double[][] euclideanMatrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cosineMatrix[i][j] = VectorSimilarityUtil.cosineSimilarity(embeddings.get(i), embeddings.get(j));
                euclideanMatrix[i][j] = VectorSimilarityUtil.euclideanDistance(embeddings.get(i), embeddings.get(j));
            }
        }

        // 打印余弦相似度矩阵
        log.info("\n【余弦相似度矩阵】");
        log.info("    " + String.format("%-12s", "文本") + " " +
                IntStream.range(0, size).mapToObj(i -> String.format("T%d", i)).collect(Collectors.joining("     ")));

        for (int i = 0; i < size; i++) {
            StringBuilder row = new StringBuilder();
            row.append(String.format("T%d: %-8s", i, texts.get(i).substring(0, Math.min(8, texts.get(i).length()))));
            for (int j = 0; j < size; j++) {
                row.append(String.format("  %.3f ", cosineMatrix[i][j]));
            }
            log.info(row.toString());
        }

        // 找出最相似和最不相似的文本对
        log.info("\n【最相似的文本对（基于余弦相似度）】");
        findTopSimilarPairs(cosineMatrix, texts, true);

        log.info("\n【最不相似的文本对（基于余弦相似度）】");
        findTopSimilarPairs(cosineMatrix, texts, false);
    }

    /**
     * 计算向量的范数（长度）
     */
    private double calculateNorm(float[] vector) {
        double sum = 0;
        for (float v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }

    /**
     * 查找最相似/最不相似的文本对
     */
    private void findTopSimilarPairs(double[][] matrix, List<String> texts, boolean findMostSimilar) {
        int size = texts.size();
        List<PairSimilarity> pairs = IntStream.range(0, size)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, size)
                        .mapToObj(j -> new PairSimilarity(i, j, matrix[i][j])))
                .sorted((a, b) -> findMostSimilar ?
                        Double.compare(b.similarity, a.similarity) :
                        Double.compare(a.similarity, b.similarity))
                .limit(3)
                .collect(Collectors.toList());

        for (PairSimilarity pair : pairs) {
            log.info("  T{} [{}] 与 T{} [{}] - 相似度: {:.4f} {}",
                    pair.i,
                    texts.get(pair.i).substring(0, Math.min(10, texts.get(pair.i).length())),
                    pair.j,
                    texts.get(pair.j).substring(0, Math.min(10, texts.get(pair.j).length())),
                    pair.similarity,
                    VectorSimilarityUtil.getCosineRating(pair.similarity)
            );
        }
    }

    /**
     * 辅助类：存储相似度结果
     */
    private static class SimilarityResult {
        int index;
        String text;
        double similarity;

        SimilarityResult(int index, String text, double similarity) {
            this.index = index;
            this.text = text;
            this.similarity = similarity;
        }
    }

    /**
     * 辅助类：存储文本对相似度
     */
    private static class PairSimilarity {
        int i;
        int j;
        double similarity;

        PairSimilarity(int i, int j, double similarity) {
            this.i = i;
            this.j = j;
            this.similarity = similarity;
        }
    }
}