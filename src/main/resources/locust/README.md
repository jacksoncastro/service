# Locust

## Subindo aplicação

Sessão destinada a mostrar como subir a aplicação

### Compilando Dockerfile

```bash
docker build . -t jackvasc/locust:latest
```

### Subir docker-compose
> **NOTA:** Compila e sobe o container, caso já esteja executando, destroi e sobe uma nova instância.

```bash
docker-compose up -d --build
```

## Exemplos

Sessão destinada a mostrar exemplos de requisição para o **locust**.

### Examplo de requisição

```json
[
    {
        "url": "/service01/api",
        "name": "Flow 01",
        "data": [
            {
                "timeout": 7500,
                "next": [
                    {
                        "service": "http://service04.default:8080/api",
                        "timeout": 5500
                    }
                ]
            }
        ]
    },
    {
        "url": "/service02/api",
        "name": "Flow 02",
        "data": [
            {
                "timeout": 7500,
                "next": [
                    {
                        "service": "http://service04.default:8080/api",
                        "timeout": 5500
                    }
                ]
            }
        ]
    },
    {
        "url": "/service03/api",
        "name": "Flow 03",
        "data": [
            {
                "timeout": 7500,
                "next": [
                    {
                        "service": "http://service04.default:8080/api",
                        "timeout": 5500
                    }
                ]
            }
        ]
    }
]
```