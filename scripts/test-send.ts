import { Kafka } from "kafkajs";

const kafka = new Kafka({
  clientId: "test-sender",
  brokers: ["127.0.0.1:9092"],
});

const producer = kafka.producer();

const run = async () => {
  await producer.connect();
  console.log("Conectado al productor de prueba...");

  const payload = {
    data: {
      id: "PAY-" + Math.floor(Math.random() * 1000),
      name: "Compra de Prueba",
      price: 99.99,
      status: "SUCCESS"
    },
    sendEmail: { status: "PENDING", message: "" },
    updateRetryJobs: { status: "PENDING", message: "" }
  };

  console.log("Enviando mensaje de pago a 'payments_retry_jobs'...");
  
  await producer.send({
    topic: "payments_retry_jobs",
    messages: [
      { value: JSON.stringify(payload) },
    ],
  });

  console.log("Mensaje enviado con éxito. Revisa la terminal del servidor.");
  await producer.disconnect();
};

run().catch(console.error);
