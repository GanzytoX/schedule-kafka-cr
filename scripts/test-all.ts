import { Kafka } from "kafkajs";

const kafka = new Kafka({
  clientId: "test-bulk-producer",
  brokers: ["127.0.0.1:9092"],
});

const producer = kafka.producer();

const run = async () => {
  await producer.connect();
  console.log("--- INICIANDO TEST MASIVO ---");

  const payMsg = {
    data: { id: "PAY-BULK-1", name: "Pago Masivo", price: 50.0 },
    sendEmail: { status: "PENDING", message: "" },
    updateRetryJobs: { status: "PENDING", message: "" },
  };

  const orderMsg = {
    data: { id: "ORD-BULK-1", customer: "Empresa X", total: 1500.0 },
    sendEmail: { status: "PENDING", message: "" },
    updateRetryJobs: { status: "PENDING", message: "" },
  };

  const prodMsg = {
    data: { id: "PROD-BULK-1", name: "Servidor Rack", warehouse: "Central" },
    sendEmail: { status: "PENDING", message: "" },
    updateRetryJobs: { status: "PENDING", message: "" },
  };

  console.log("Enviando los 3 tópicos simultáneamente...");

  await Promise.all([
    producer.send({
      topic: "payments_retry_jobs",
      messages: [{ value: JSON.stringify(payMsg) }],
    }),
    producer.send({
      topic: "order_retry_jobs",
      messages: [{ value: JSON.stringify(orderMsg) }],
    }),
    producer.send({
      topic: "product_retry_jobs",
      messages: [{ value: JSON.stringify(prodMsg) }],
    }),
  ]);

  console.log(
    "✅ Los 3 mensajes han sido enviados. ¡Mira la terminal de Spring Boot!",
  );
  await producer.disconnect();
};

run().catch(console.error);
