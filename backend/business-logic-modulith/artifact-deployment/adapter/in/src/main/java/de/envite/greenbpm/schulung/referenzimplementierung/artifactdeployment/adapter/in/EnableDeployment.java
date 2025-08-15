package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.in;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Aktiviert das automatische Deployment von *.bpmn-, *.dmn- und *.form-Ressourcen beim Start der Anwendung.
 * Loggt einen Fehler, wenn das Deployment nicht möglich ist. Stoppt den Start der Anwendung nicht.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({DeploymentServiceConfiguration.class, DeploymentRunner.class})
public @interface EnableDeployment {
}
