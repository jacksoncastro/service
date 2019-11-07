from locust import HttpLocust, TaskSet, task
import json
import os

class RestTasks(TaskSet):

    @task
    def index(self):
        speedup_request = os.environ['SPEEDUP_REQUEST']
        json_request = json.loads(speedup_request)
        headers = {'content-type': 'application/json','Accept-Encoding':'gzip'}
        for request in json_request:
	        self.client.post(request['url'], data=json.dumps(request['data']),
	        headers=headers,
	        name = request['name'])

class WebsiteUser(HttpLocust):
    task_set = RestTasks
