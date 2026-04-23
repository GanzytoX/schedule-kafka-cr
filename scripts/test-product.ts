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
      id: "PROD-55-ABC",
      name: "Monitor 4K UltraWide",
      stock_retry: 5,
      warehouse: "Norte-01"
    },
    sendEmail: { status: "PENDING", message: "" },
    updateRetryJobs: { status: "PENDING", message: "" }
  };

  console.log("Enviando mensaje de PRODUCTO a 'product_retry_jobs'...");

  await producer.send({
    topic: 'product_retry_jobs',
    messages: [
      { value: JSON.stringify(message) }
    ],
  });

  console.log('Mensaje de producto enviado con éxito. Revisa la terminal del servidor.');
  await producer.disconnect();
};

run().catch(console.error);
