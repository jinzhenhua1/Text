package com.jzh.mvp.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * <p>Dagger2 custom scope for application.</p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/03/01 10:06
 */
@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PerApplication {
	// Nothing need to do here.
}
