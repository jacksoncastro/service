# Service Spring

### Build image

```bash
mvn -DskipTests install dockerfile:build
```

```bash
docker tag br.com.jackson/service:latest jackvasc/speedup:latest
```

```bash
kubectl delete all -l group=speedup && kubectl apply -f speedup.yaml
```