package com.github.abchau.oss.archexamples.subscription;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchUnitTests {
    
    // @Test
    // public void driving_adapter_should_only_depend_on_packages() {        
    //     JavaClasses jc = new ClassFileImporter()
	// 		.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
	// 		.importPackages("com.github.abchau.oss.archexamples.subscription");
        
    //     ArchRule r1 = classes()
    //       .that()
	// 	  	.resideInAPackage("..drivingadapter..")
    //       .should()
	// 	  	.onlyDependOnClassesThat()
    //       		.resideInAnyPackage(
	// 				// JDK
	// 				"java..", 
	// 				"jakarta.."
	// 				// Spring
	// 				, "org.springframework.."
	// 				// Apache Logging
	// 				, "org.apache.logging.."
	// 				// jmolecules Annotations
	// 				, "org.jmolecules.."
	// 				// // Application Core - Domain
	// 				// , "com.github.abchau.oss.archexamples.subscription.application.."
	// 				// Application Core - Driving Port
	// 				, "com.github.abchau.oss.archexamples.subscription.application.drivingport..");
    
	// 	r1.check(jc);
    // }

	@Test
    public void should_follow_layered_architecture() {
        
        JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.github.abchau.oss.archexamples.subscription");
        
        LayeredArchitecture arch = layeredArchitecture()
           // Define layers
          .layer("Driving Adapter").definedBy("..drivingadapter..")
          .layer("Driving Port").definedBy("..drivingport..")
          .layer("Business Logic").definedBy("..businesslogic..")
          .layer("Driven Port").definedBy("..drivenport..")
          .layer("Driven Adapter").definedBy("..drivenadapter..")
          // Add constraints
          .whereLayer("Driving Adapter").mayNotBeAccessedByAnyLayer()
          .whereLayer("Driving Port").mayOnlyBeAccessedByLayers("Business Logic", "Driving Adapter")
          .whereLayer("Business Logic").mayOnlyBeAccessedByLayers("Driving Port")
          .whereLayer("Driven Port").mayOnlyBeAccessedByLayers("Business Logic", "Driven Adapter")
          .whereLayer("Driven Adapter").mayOnlyBeAccessedByLayers("Business Logic");
        
        arch.check(jc);
    }

	@Test
    public void should_be_free_of_cycle() {
		JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.github.abchau.oss.archexamples.subscription");
        
		ArchRule r1 = SlicesRuleDefinition.slices()
			.matching("..com.github.abchau.oss.archexamples.subscription.(*)..")
			.should().beFreeOfCycles();
    
		r1.check(jc);
	}

}
