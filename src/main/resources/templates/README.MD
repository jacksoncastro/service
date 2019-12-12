## Install by Helm prometheus

```bash
helm install stable/prometheus-operator --name prometheus-operator --namespace monitoring
```

## Up and Down speedup template

```bash
kubectl delete all -l group=speedup && kubectl apply -f speedup.yaml
```

```bash
VBoxManage startvm k8s-master --type headless
VBoxManage startvm k8s-node01 --type headless
VBoxManage startvm k8s-node02 --type headless
```

```bash
VBoxManage controlvm k8s-master poweroff
VBoxManage controlvm k8s-node01 poweroff
VBoxManage controlvm k8s-node02 poweroff
```

### Ingress

```bash
kubectl delete --ignore-not-found=true -f ingress.yaml && kubectl apply -f ingress.yaml
```