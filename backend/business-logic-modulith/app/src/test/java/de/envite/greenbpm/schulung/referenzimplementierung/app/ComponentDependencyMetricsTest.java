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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    void generate_system_out_html_metrics_report() {
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

    @Test
    void generate_html_metrics_report() throws IOException {
        JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE);
        Set<JavaPackage> packages = classes.getPackage(BASE_PACKAGE).getSubpackages();

        MetricsComponents<JavaClass> components = MetricsComponents.fromPackages(packages);
        ComponentDependencyMetrics componentMetrics = ArchitectureMetrics.componentDependencyMetrics(components);
        VisibilityMetrics visibilityMetrics = ArchitectureMetrics.visibilityMetrics(components);

        try (PrintWriter out = new PrintWriter(new FileWriter("target/component-metrics.html"))) {
            out.println("<html><head><title>Component Dependency Metrics</title>");
            out.println("<style>table { border-collapse: collapse; }"
                    + "th, td { border: 1px solid #ccc; padding: 4px; }"
                    + "th { background: #eee; }</style>");
            out.println("</head><body>");

            out.println("<h1>Component Dependency Metrics</h1>");

            out.println("<h2>Global Visibility</h2>");
            out.println("<table>");
            out.printf("<tr><th>Average Relative Visibility</th><td>%.2f</td></tr>%n",
                    visibilityMetrics.getAverageRelativeVisibility());
            out.printf("<tr><th>Global Relative Visibility</th><td>%.2f</td></tr>%n",
                    visibilityMetrics.getGlobalRelativeVisibility());
            out.println("</table>");

            out.println("<h2>Packages of Interest</h2>");
            out.println("<table>");
            out.println("<tr><th>Package</th><th>Relative Visibility</th>"
                    + "<th>Efferent Coupling (Ce)</th><th>Afferent Coupling (Ca)</th>"
                    + "<th>Instability (I)</th><th>Abstractness (A)</th>"
                    + "<th>Distance from Main Sequence (D)</th></tr>");

            for (String p : PACKAGES_OF_INTEREST) {
                out.printf("<tr><td>%s</td>"
                                + "<td>%.2f</td><td>%d</td><td>%d</td>"
                                + "<td>%.2f</td><td>%.2f</td><td>%.2f</td></tr>%n",
                        p.replace(BASE_PACKAGE + ".", ""),
                        visibilityMetrics.getRelativeVisibility(p),
                        componentMetrics.getEfferentCoupling(p),
                        componentMetrics.getAfferentCoupling(p),
                        componentMetrics.getInstability(p),
                        componentMetrics.getAbstractness(p),
                        componentMetrics.getNormalizedDistanceFromMainSequence(p));
            }

            out.println("</table>");
            out.println("</body></html>");
        }
    }
}
