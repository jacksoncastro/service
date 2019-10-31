from locust import HttpLocust, TaskSet, task
import json

class RestTasks(TaskSet):

    @task
    def index(self):
        payload = [
            {
                "service": "http://service02.default:8080/api",
                "sleep": 1000,
                "timeout": 5000,
                "next": [
                    {
                        "service": "http://service03.default:8080/api",
                        "sleep": 1000,
                        "timeout": 5000
                    }
                ]
            },
            {
                "service": "http://service03.default:8080/api",
                "sleep": 1000,
                "timeout": 5000
            },
            {
                "service": "http://service04.default:8080/api",
                "sleep": 1000,
                "timeout": 1000
            }
        ]
        headers = {'content-type': 'application/json','Accept-Encoding':'gzip'}
        self.client.post("/service01/api", data=json.dumps(payload),
        headers=headers,
        name = "Service 01")

class WebsiteUser(HttpLocust):
    task_set = RestTasks
