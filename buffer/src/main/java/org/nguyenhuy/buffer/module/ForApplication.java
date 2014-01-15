/**
 * Reference: https://github.com/square/dagger/blob/master/examples/android-activity-graphs/src/main/java/com/example/dagger/activitygraphs/ForApplication.java
 */
package org.nguyenhuy.buffer.module;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}