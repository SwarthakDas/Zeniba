export async function getEmbedding(text) {
  const response = await fetch(
    "https://api-inference.huggingface.co/models/intfloat/multilingual-e5-large",
    {
      headers: {
        Authorization: `Bearer ${process.env.HF_TOKEN}`,
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify({ inputs: text }),
    }
  );
  const raw = await response.text();
  console.log("HF raw response:", raw);

  if (!response.ok) {
    throw new Error(`HF API failed: ${response.status} - ${raw}`);
  }

  let result;
  try {
    result = JSON.parse(raw);
  } catch (err) {
    throw new Error("Failed to parse HF response as JSON: " + raw);
  }

  return result;
}
