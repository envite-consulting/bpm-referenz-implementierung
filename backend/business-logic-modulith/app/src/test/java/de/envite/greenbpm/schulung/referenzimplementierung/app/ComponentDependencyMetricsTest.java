package de.envite.greenbpm.schulung.referenzimplementierung.app;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.metrics.ArchitectureMetrics;
import com.tngtech.archunit.library.metrics.ComponentDependencyMetrics;
import com.tngtech.archunit.library.metrics.MetricsComponents;
import com.tngtech.archunit.library.metrics.VisibilityMetrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

class ComponentDependencyMetricsTest {

    private static final String BASE_PACKAGE = "de.envite.greenbpm.schulung.referenzimplementierung";
    private static final String BESTELLUNG_PACKAGE = BASE_PACKAGE + ".bestellung";
    private static final String FAHRZEUG_PACKAGE = BASE_PACKAGE + ".fahrzeug";
    
    private static final List<String> PACKAGES_OF_INTEREST = List.of(BESTELLUNG_PACKAGE, FAHRZEUG_PACKAGE); 

    private String printableMetrics(final String packageName, final String metricName) {
        return packageName.replace(BASE_PACKAGE + ".", "") + ": " + metricName + ": ";
    }
    
    private void print(final String message, Object...args) {
        final String printableArgs = String.join(" ", Arrays.stream(args).map(String::valueOf).toList());
        System.out.println(message + printableArgs);
    }

    @Test
    void print_metrics() {
        JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE);
        Set<JavaPackage> packages = classes.getPackage(BASE_PACKAGE).getSubpackages();

        MetricsComponents<JavaClass> components = MetricsComponents.fromPackages(packages);
        ComponentDependencyMetrics componentDependencyMetrics = ArchitectureMetrics.componentDependencyMetrics(components);
        VisibilityMetrics visibilityMetrics = ArchitectureMetrics.visibilityMetrics(components);

        print("======================================");
        print("==== Component Dependency Metrics ====");
        print("======================================");

        print(printableMetrics("ALL", "Average Relative Visibility"), visibilityMetrics.getAverageRelativeVisibility());
        print(printableMetrics("ALL", "Global Relative Visibility"), visibilityMetrics.getGlobalRelativeVisibility());

        PACKAGES_OF_INTEREST.forEach(p -> {
            print(printableMetrics(p, "Relative Visibility"), visibilityMetrics.getRelativeVisibility(p));

            print(printableMetrics(p, "Efferent Coupling"), componentDependencyMetrics.getEfferentCoupling(p));
            print(printableMetrics(p, "Afferent Coupling"), componentDependencyMetrics.getAfferentCoupling(p));
            print(printableMetrics(p, "Instability"), componentDependencyMetrics.getInstability(p));
            print(printableMetrics(p, "Abstractness"), componentDependencyMetrics.getAbstractness(p));
            print(printableMetrics(p, "Distance from Main Sequence"), componentDependencyMetrics.getNormalizedDistanceFromMainSequence(p));
        });
    }
}
