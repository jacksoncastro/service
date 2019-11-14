# Locust

## Subindo aplicação

Sessão destinada a mostrar como subir a aplicação

### Compilando Dockerfile

```bash
docker build . -t jackvasc/locust:latest
```

### Configuração Locust

Modifique o arquivo `environment` dentro da pasta `config` do locust.

**Atuais váriaveis:**

|    VARIABLE   |           VALUE          | DESCRIPTION                 |
|:-------------:|:------------------------:|-----------------------------|
| ATTACKED_HOST | http://speedup.local.com | Host that will be attacked. |
|  LOCUST_OPTS  |       -c 100 -r 10       | Options for locust.         |

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
        "data": {
            "average": 1000,
            "deviation": 10,
            "speedup": {
                "type": "REAL",
                "value": 20
            },
            "next": [
                {
                    "service": "http://service02.default:8080/api",
                    "average": 1100,
                    "deviation": 10,
                    "next": [
                        {
                            "service": "http://service04.default:8080/api",
                            "average": 1300,
                            "deviation": 20
                        }
                    ]
                },
                {
                    "service": "http://service03.default:8080/api",
                    "average": 1400,
                    "deviation": 20
                }
            ]
        }
    }
]
```