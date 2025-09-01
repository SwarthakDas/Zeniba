export async function getEmbedding(text) {
  const response = await fetch(
    "https://router.huggingface.co/hf-inference/models/Qwen/Qwen3-Embedding-0.6B/pipeline/feature-extraction",
    {
      headers: {
        Authorization: `Bearer ${process.env.HF_TOKEN}`,
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify({ inputs: text }),
    }
  );
  const result = await response.json();
  return result;
}

export function cosineSimilarity(vecA, vecB) {
  if (!vecA.length || !vecB.length) return 0;

  const dotProduct = vecA.reduce((sum, a, i) => sum + a * (vecB[i] || 0), 0);
  const normA = Math.sqrt(vecA.reduce((sum, a) => sum + a * a, 0));
  const normB = Math.sqrt(vecB.reduce((sum, b) => sum + b * b, 0));

  if (!normA || !normB) return 0;
  return dotProduct / (normA * normB);
}
