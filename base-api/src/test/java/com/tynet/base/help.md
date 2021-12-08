# NOTE

## JUnit4 -> JUnit5升级说明

#### JUnit4 与 JUnit5 常用注解对比

> https://blog.csdn.net/winteroak/article/details/80591598

| JUnit4 | JUnit5 | 特点 |
| :---: | :---: | :---: |
| @BeforeClass | @BeforeAll | 在当前类的所有测试方法之前执行。注解在【静态方法】 |
| @AfterClass | @AfterAll | 在当前类中的所有测试方法之后执行。注解在【静态方法】 |
| @Before | @BeforeEach | 在每个测试方法之前执行。注解在【非静态方法】 |
| @After | @AfterEach | 在每个测试方法之后执行。注解在【非静态方法】 |
| @Test | @Test | 表示该方法是一个测试方法。JUnit5与JUnit4的@Test注解不同的是，它没有声明任何属性 |
| @Ignore | @Disabled | 禁用一个测试类或测试方法 |
| @Category | @Tag | 禁用一个测试类或测试方法 |
| @RunWith | @ExtendWith | 确定这个类怎么运行的 |
