import { Kafka } from 'kafkajs';

const kafka = new Kafka({
  clientId: 'test-producer',
  brokers: ['127.0.0.1:9092']
});

const producer = kafka.producer();

const run = async () => {
  await producer.connect();
  console.log('Conectado al productor de prueba...');

  const message = {
    data: {
      id: "ORD-999-XYZ",
      customer: "Juan Perez",
      total: 540.50,
      items: ["Laptop Pro", "Mouse Gamer"]
    },
    sendEmail: { status: "PENDING", message: "" },
    updateRetryJobs: { status: "PENDING", message: "" }
  };

  console.log("Enviando mensaje de ORDEN a 'order_retry_jobs'...");

  await producer.send({
    topic: 'order_retry_jobs',
    messages: [
      { value: JSON.stringify(message) }
    ],
  });

  console.log('Mensaje de orden enviado con éxito. Revisa la terminal del servidor.');
  await producer.disconnect();
};

run().catch(console.error);
