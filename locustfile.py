import time

from locust import HttpUser, task, between


class BalanceModule(HttpUser):
  wait_time = between(1, 2.5)

  @task
  def kyValueList(self):
    self.client.get(
      "/keyValues"
    )
