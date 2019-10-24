from locust import HttpLocust, TaskSet, task

class RestTasks(TaskSet):

    @task
    def index(self):
    	# rest
        self.client.get("/api/rest/veiculos") # Lista veiculos
        self.client.get("/api/rest/veiculo/NUX5555") # buscar veiculo por placa

    	# restql
        self.client.get("/api/restql/veiculos") # Lista veiculos
        self.client.get("/api/restql/veiculo/NUX5555") # buscar veiculo por placa

        # graphql
        self.client.get("/api/graphql/veiculo") # Lista veiculos
        self.client.get("/api/graphql/veiculo/1") # buscar veiculo por placa



class WebsiteUser(HttpLocust):
    task_set = RestTasks
