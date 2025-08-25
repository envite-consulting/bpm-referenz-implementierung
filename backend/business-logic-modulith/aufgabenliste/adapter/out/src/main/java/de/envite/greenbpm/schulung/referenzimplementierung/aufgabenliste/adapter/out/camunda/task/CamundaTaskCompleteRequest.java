package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import java.util.Map;

record CamundaTaskCompleteRequest(Map<String, Object> variables) {}
