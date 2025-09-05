package de.envite.greenbpm.schulung.referenzimplementierung.app;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.apache.commons.lang3.ArrayUtils;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = CleanArchitectureTest.BASE_PACKAGE,
        importOptions = ImportOption.DoNotIncludeTests.class
)
class CleanArchitectureTest {

    static final String BASE_PACKAGE = "de.envite.greenbpm.schulung.referenzimplementierung";

    private static final String DOMAIN_SERVICE = "..domain.service..";
    private static final String DOMAIN_MODEL = "..domain.model..";
    private static final String USE_CASE = "..usecase..";
    private static final String USE_CASE_IN = "..usecase.in..";
    private static final String USE_CASE_OUT = "..usecase.out..";
    private static final String ADAPTER = "..adapter..";
    private static final String ADAPTER_IN = "..adapter.in..";
    private static final String ADAPTER_OUT = "..adapter.out..";

    /**
     * Pakete, die nicht als Technologie angesehen werden und daher in der Domaine genutzt werden koennen
     */
    private static final String[] NON_TECHNOLOGY_PACKAGE = {
            BASE_PACKAGE + "*.domain.model..",
            "lombok..",
            "java.lang..",
            "java.time..",
            "java.util..",
            "io.github.domainprimitives.*."
    };

    /**
     * Pakete, die nicht als Technologie angesehen werden und daher in der Domaine genutzt werden koennen
     */
    private static final String[] NON_TECHNOLOGY_PACKAGE_WITH_DEPENDENCY_INJECTION = ArrayUtils.addAll(
            NON_TECHNOLOGY_PACKAGE,
            "org.springframework.stereotype.*");

    @ArchTest
    static ArchRule domain_services_should_reside_in_domain_service_package = classes()
            .that().haveSimpleNameEndingWith("DomainService")
            .should().resideInAPackage(DOMAIN_SERVICE);

    @ArchTest
    static ArchRule mappers_should_reside_in_adapter = classes()
            .that().haveSimpleNameEndingWith("Mapper")
            .should().resideInAPackage(ADAPTER);

    @ArchTest
    static ArchRule domain_services_should_implement_use_case_in = classes()
            .that().resideInAPackage(DOMAIN_SERVICE)
            .and().haveSimpleNameEndingWith("DomainService")
            .should().implement(resideInAPackage(USE_CASE_IN))
            .andShould().notImplement(resideInAPackage(USE_CASE_OUT));

    @ArchTest
    static ArchRule domain_services_should_not_use_adapters = noClasses()
            .that().resideInAPackage(DOMAIN_SERVICE)
            .should().accessClassesThat().resideInAPackage(ADAPTER);

    @ArchTest
    static ArchRule domain_services_should_not_use_technology = noClasses()
            .that().resideInAPackage(DOMAIN_SERVICE)
            .should().onlyAccessClassesThat().resideInAnyPackage(NON_TECHNOLOGY_PACKAGE_WITH_DEPENDENCY_INJECTION);

    @ArchTest
    static ArchRule domain_model_should_not_use_adapters = noClasses()
            .that().resideInAPackage(DOMAIN_MODEL)
            .should().accessClassesThat().resideInAPackage(ADAPTER);

    @ArchTest
    static ArchRule domain_model_should_not_use_use_cases = noClasses()
            .that().resideInAPackage(DOMAIN_MODEL)
            .should().accessClassesThat().resideInAPackage(USE_CASE);

    @ArchTest
    static ArchRule domain_model_should_not_use_technology = noClasses()
            .that().resideInAPackage(DOMAIN_MODEL)
            .should().onlyAccessClassesThat().resideInAnyPackage(NON_TECHNOLOGY_PACKAGE);

    @ArchTest
    static ArchRule adapter_in_should_not_use_adapter_out = noClasses()
            .that().resideInAPackage(ADAPTER_IN)
            .should().accessClassesThat().resideInAPackage(ADAPTER_OUT);

    @ArchTest
    static ArchRule adapter_out_should_not_use_adapter_in = noClasses()
            .that().resideInAPackage(ADAPTER_OUT)
            .should().accessClassesThat().resideInAPackage(ADAPTER_IN);
}
