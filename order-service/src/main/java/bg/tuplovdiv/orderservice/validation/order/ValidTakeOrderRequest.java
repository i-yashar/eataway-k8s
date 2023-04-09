package bg.tuplovdiv.orderservice.validation.order;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = TakeOrderValidator.class)
public @interface ValidTakeOrderRequest {
    String message() default "Invalid take order request.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
