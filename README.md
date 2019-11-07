# Service Spring

### Build image

```bash
mvn -DskipTests install dockerfile:build
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

### Exemplo de json
```json
[
    {
        "timeout": 10000,
        "next": [
        	{
        		"service": "http://service02.default:8080/api",
        		"timeout": 10000
        	}
        ]
    }
]
```