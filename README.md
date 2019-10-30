# Service Spring

### Build image

```bash
mvn -DskipTests install dockerfile:build
```

```bash
docker tag br.com.jackson/service:latest jackvasc/speedup:latest
```

```bash
docker push jackvasc/speedup:latest
```

```bash
kubectl delete all -l group=speedup && kubectl apply -f speedup.yaml
```

```bash
kubectl port-forward -n monitoring prometheus-prometheus-operator-prometheus-0 9090
```


```json
[
    {
        "service": "http://localhost:8080/api",
        "sleep": 1000,
        "timeout": 5000,
        "next": [
            {
                "service": "http://localhost:8080/api",
                "sleep": 1000,
                "timeout": 5000
            }
        ]
    },
    {
        "service": "http://localhost:8080/api",
        "sleep": 1000,
        "timeout": 5000
    },
    {
        "service": "http://localhost:8080/api",
        "sleep": 1000,
        "timeout": 1000
    }
]
```