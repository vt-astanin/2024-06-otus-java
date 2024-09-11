package runners;

import annotations.After;
import annotations.Before;
import annotations.Test;
import tests.AnyTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {
    public static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    public static void runTests(Class<?> testClass) {
	Map<String, Integer> testResults = testResults();

	Method[] methods = testClass.getDeclaredMethods();
	List<Method> beforeMethods = getMethodsByAnnotation(methods, Before.class);
	List<Method> afterMethods = getMethodsByAnnotation(methods, After.class);
	List<Method> testMethods = getMethodsByAnnotation(methods, Test.class);
	//testMethods(methods, beforeMethods, afterMethods, testMethods);

	for (Method testMethod : testMethods) {
	    executeTest(testClass, testMethod, beforeMethods, afterMethods, testResults);
	}

	printResults(testResults);
    }

    private static Map<String, Integer> testResults() {
	Map<String, Integer> results = new HashMap<>();
	results.put("total", 0);
	results.put("passed", 0);
	results.put("failed", 0);
	return results;
    }

    @SuppressWarnings("unused")
    private static void testMethods(Method[] methods, List<Method> beforeMethods, List<Method> afterMethods, List<Method> testMethods) {
	for (Method method : methods) {
	    if (method.isAnnotationPresent(Before.class)) {
		beforeMethods.add(method);
	    } else if (method.isAnnotationPresent(After.class)) {
		afterMethods.add(method);
	    } else if (method.isAnnotationPresent(Test.class)) {
		testMethods.add(method);
	    }
	}
    }

    private static List<Method> getMethodsByAnnotation(Method[] methods, Class<? extends Annotation> annotationClass) {
	List<Method> annotatedMethods = new ArrayList<>();
	for (Method method : methods) {
	    if (method.isAnnotationPresent(annotationClass)) {
		annotatedMethods.add(method);
	    }
	}
	return annotatedMethods;
    }

    private static void executeTest(Class<?> testClass, Method testMethod, List<Method> beforeMethods, List<Method> afterMethods,
		    Map<String, Integer> testResults) {
	testResults.put("total", testResults.get("total") + 1);
	Object testInstance = createInstance(testClass);

	try {
	    executeMethods(beforeMethods, testInstance);
	    testMethod.invoke(testInstance);
	    testResults.put("passed", testResults.get("passed") + 1);
	} catch (Exception e) {
	    testResults.put("failed", testResults.get("failed") + 1);
	    logger.error("Test {} - {}", testMethod.getName(), e.getCause().getMessage());
	} finally {
	    executeMethods(afterMethods, testInstance);
	}
    }

    private static Object createInstance(Class<?> clazz) {
	try {
	    return clazz.getDeclaredConstructor().newInstance();
	} catch (Exception e) {
	    throw new RuntimeException("Error create instance", e);
	}
    }

    private static void executeMethods(List<Method> methods, Object instance) {
	for (Method method : methods) {
	    try {
		method.invoke(instance);
	    } catch (Exception e) {
		logger.error("Error  method execution: {} - {}", method.getName(), e.getMessage());
	    }
	}
    }

    private static void printResults(Map<String, Integer> testResults) {
	logger.info("Total tests: {}", testResults.get("total"));
	logger.info("Passed: {}", testResults.get("passed"));
	logger.info("Failed: {}", testResults.get("failed"));
    }

    public static void main(String[] args) {
	runTests(AnyTest.class);
    }
}
