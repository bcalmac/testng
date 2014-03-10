package test.dependent;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Along with the pair InterleavedDependentMethodsAnotherTest this asserts
 * that methods from different classes are not interleaved as follows:
 *
 * <pre>
 * before class1
 *     test class1.freeMethod
 * before class2
 *     test class2.freeMethod
 *     test class1.dependentMethod
 * after class1
 *     test class2.dependentMethod
 * after class2
 * </pre>
 *
 * The desired behavior is:
 *
 * <pre>
 * before class1
 *     test class1.freeMethod
 *     test class1.dependentMethod
 * after class1
 * before class2
 *     test class2.freeMethod
 *     test class2.dependentMethod
 * after class2
 * </pre>
 */
public class InterleavedDependentMethodsTest {

  private static int activeClassCount = 0;

  @BeforeClass
  public void beforeClass() {
    activeClassCount++;
  }

  @AfterClass
  public void afterClass() {
    activeClassCount--;
  }

  @Test
  public void freeMethod() {
    assertEquals(activeClassCount, 1);
  }

  @Test(dependsOnMethods = "freeMethod")
  public void dependentMethod() {
    assertEquals(activeClassCount, 1);
  }
}
