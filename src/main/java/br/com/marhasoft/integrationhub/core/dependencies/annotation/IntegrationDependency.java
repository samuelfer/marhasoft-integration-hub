package br.com.marhasoft.integrationhub.core.dependency.annotation;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IntegrationDependency {

    IntegrationModule module();

    IntegrationOperation operation();

    IntegrationAction action();

    int order() default 0;

}