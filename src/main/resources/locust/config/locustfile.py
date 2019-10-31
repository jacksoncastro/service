from locust import HttpLocust, TaskSet, task

class RestTasks(TaskSet):

    payload = [
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

    @task
    def index(self):
        self.client.post("/service01/api", json=payload)

class WebsiteUser(HttpLocust):
    task_set = RestTasks
