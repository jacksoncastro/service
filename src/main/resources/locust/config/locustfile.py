from locust import HttpLocust, TaskSet, task
import json

class RestTasks(TaskSet):

    @task
    def index(self):
        # -------------
        payload01 = [
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
        headers = {'content-type': 'application/json','Accept-Encoding':'gzip'}
        self.client.post("/service01/api", data=json.dumps(payload01),
        headers=headers,
        name = "Flow 01")

        # -------------
        payload02 = [
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
        headers = {'content-type': 'application/json','Accept-Encoding':'gzip'}
        self.client.post("/service02/api", data=json.dumps(payload02),
        headers=headers,
        name = "Flow 02")

        # -------------
        payload03 = [
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
        headers = {'content-type': 'application/json','Accept-Encoding':'gzip'}
        self.client.post("/service03/api", data=json.dumps(payload03),
        headers=headers,
        name = "Flow 03")


class WebsiteUser(HttpLocust):
    task_set = RestTasks
