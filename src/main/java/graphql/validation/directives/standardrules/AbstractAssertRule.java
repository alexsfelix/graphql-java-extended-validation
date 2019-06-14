package graphql.validation.directives.standardrules;

import graphql.GraphQLError;
import graphql.Scalars;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLInputType;
import graphql.validation.directives.AbstractDirectiveValidationRule;
import graphql.validation.rules.ValidationRuleEnvironment;

import java.util.Collections;
import java.util.List;

abstract class AbstractAssertRule extends AbstractDirectiveValidationRule {

    public AbstractAssertRule(String name) {
        super(name);
    }


    @Override
    public boolean appliesToType(GraphQLInputType inputType) {
        return isOneOfTheseTypes(inputType,
                Scalars.GraphQLBoolean
        );
    }


    @Override
    public List<GraphQLError> runValidation(ValidationRuleEnvironment ruleEnvironment) {
        Object argumentValue = ruleEnvironment.getFieldOrArgumentValue();
        //null values are valid
        if (argumentValue == null) {
            return Collections.emptyList();
        }

        GraphQLDirective directive = ruleEnvironment.getContextObject(GraphQLDirective.class);

        boolean isTrue = asBoolean(argumentValue);
        if (!isOK(isTrue)) {
            return mkError(ruleEnvironment, directive, mkMessageParams(
                    "fieldOrArgumentValue", argumentValue));

        }
        return Collections.emptyList();
    }

    protected abstract boolean isOK(boolean isTrue);


}