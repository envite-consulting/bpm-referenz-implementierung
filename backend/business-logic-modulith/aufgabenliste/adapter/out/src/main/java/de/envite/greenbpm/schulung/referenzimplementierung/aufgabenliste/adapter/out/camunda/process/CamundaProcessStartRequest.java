package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import java.util.Map;

record CamundaProcessStartRequest(Map<String, Object> variables) {}
