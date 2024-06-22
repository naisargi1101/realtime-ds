from kafka import KafkaProducer
import json
import time

producer = KafkaProducer(bootstrap_servers='localhost:9092',
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

def get_data():
    # Replace this with actual data retrieval logic
    return {"timestamp": time.time(), "value": 42}

while True:
    data = get_data()
    producer.send('data_stream', data)
    print(f"Produced: {data}")
    time.sleep(1)
