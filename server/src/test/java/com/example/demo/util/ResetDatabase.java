package com.example.demo.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.test.context.TestExecutionListeners;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestExecutionListeners(
  mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
  listeners = { ResetDatabaseTestExecutionListener.class }
)
public @interface ResetDatabase {
}
