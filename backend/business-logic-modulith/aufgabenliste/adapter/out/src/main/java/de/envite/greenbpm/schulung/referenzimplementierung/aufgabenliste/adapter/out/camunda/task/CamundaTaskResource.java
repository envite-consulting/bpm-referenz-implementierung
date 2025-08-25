package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;


record CamundaTaskResource(String id, String name, String assignee, String created, String formKey) {}
