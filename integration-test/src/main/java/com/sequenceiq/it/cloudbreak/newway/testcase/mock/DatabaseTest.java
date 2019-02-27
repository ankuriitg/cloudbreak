package com.sequenceiq.it.cloudbreak.newway.testcase.mock;

import static com.sequenceiq.it.cloudbreak.newway.context.RunningParameter.expectedMessage;
import static com.sequenceiq.it.cloudbreak.newway.context.RunningParameter.key;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sequenceiq.cloudbreak.api.endpoint.v4.database.base.DatabaseType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.requests.DatabaseV4Request;
import com.sequenceiq.it.cloudbreak.newway.assertion.database.DatabaseExistsAssertion;
import com.sequenceiq.it.cloudbreak.newway.assertion.database.DatabaseTestAccessDeniedAssertion;
import com.sequenceiq.it.cloudbreak.newway.context.Description;
import com.sequenceiq.it.cloudbreak.newway.context.MockedTestContext;
import com.sequenceiq.it.cloudbreak.newway.context.TestCaseDescription;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.entity.database.DatabaseEntity;
import com.sequenceiq.it.cloudbreak.newway.entity.database.DatabaseTestEntity;
import com.sequenceiq.it.cloudbreak.newway.testcase.AbstractIntegrationTest;
import com.sequenceiq.it.util.LongStringGeneratorUtil;

public class DatabaseTest extends AbstractIntegrationTest {

    private static final String DB_TYPE_PROVIDER = "databaseTypeTestProvider";

    private static final String INVALID_ATTRIBUTE_PROVIDER = "databaseInvalidAttributesTestProvider";

    private static final String DATABASE_PROTOCOL = "jdbc:postgresql://";

    private static final String DATABASE_HOST_PORT_DB = "somedb.com:5432/mydb";

    private static final String DATABASE_USERNAME = "username";

    private static final String DATABASE_PASSWORD = "password";

    private static final Class<MockedTestContext> TEST_CONTEXT_CLASS = MockedTestContext.class;

    @Inject
    private LongStringGeneratorUtil longStringGeneratorUtil;

    @BeforeMethod
    public void beforeMethod(Object[] data) {
        TestContext testContext = (TestContext) data[0];
        createDefaultUser(testContext);
        createDefaultCredential(testContext);
        createDefaultEnvironment(testContext);
    }

    @AfterMethod(alwaysRun = true)
    public void tear(Object[] data) {
        ((TestContext) data[0]).cleanupTestContextEntity();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "Database recreate test",
            when = "calling create and then delete and recreate Database",
            then = "the second create should be successful as well")
    public void createAndDeleteAndCreateWithSameNameThenShouldRecreatedDatabase(TestContext testContext) {
        String databaseName = getNameGenerator().getRandomNameForResource();
        testContext
                .given(DatabaseEntity.class)
                .withName(databaseName)
                .when(DatabaseEntity.post(), key(databaseName))
                .when(DatabaseEntity.list(), key(databaseName))
                .then(DatabaseExistsAssertion.getAssertion(databaseName, 1), key(databaseName))
                .when(DatabaseEntity.deleteV2(), key(databaseName))
                .when(DatabaseEntity.list(), key(databaseName))
                .then(DatabaseExistsAssertion.getAssertion(databaseName, 0), key(databaseName))
                .when(DatabaseEntity.post(), key(databaseName))
                .when(DatabaseEntity.list(), key(databaseName))
                .then(DatabaseExistsAssertion.getAssertion(databaseName, 1), key(databaseName))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "Database create twice with same name test",
            when = "calling create and then recreate Database",
            then = "the second create should fail fail because duplicated record")
    public void createAndCreateWithSameNameThenShouldThrowBadRequestException(TestContext testContext) {
        String databaseName = getNameGenerator().getRandomNameForResource();
        testContext
                .given(DatabaseEntity.class)
                .withName(databaseName)
                .when(DatabaseEntity.post(), key(databaseName))
                .when(DatabaseEntity.list(), key(databaseName))
                .then(DatabaseExistsAssertion.getAssertion(databaseName, 1), key(databaseName))
                .when(DatabaseEntity.post(), key(databaseName))
                .expect(BadRequestException.class, key(databaseName))
                .validate();
    }

    @Test(dataProvider = DB_TYPE_PROVIDER)
    public void testCreateDatabaseWithTypeAndTestConnection(
            TestContext testContext,
            DatabaseType type,
            @Description TestCaseDescription testCaseDescription) {
        String databaseName = getNameGenerator().getRandomNameForResource();
        testContext
                .withDescription(testCaseDescription)
                .given(DatabaseEntity.class)
                .withType(type.name())
                .withName(databaseName)
                .when(DatabaseEntity.post(), key(databaseName))
                .when(DatabaseEntity.list(), key(databaseName))
                .then(DatabaseExistsAssertion.getAssertion(databaseName, 1), key(databaseName))
                .given(DatabaseTestEntity.class)
                .withExistingName(databaseName)
                .when(DatabaseTestEntity.testConnection(), key(databaseName))
                .validate();
    }

    @Test(dataProvider = INVALID_ATTRIBUTE_PROVIDER)
    public void testCreateDatabaseWithInvalidAttribute(
            TestContext testContext,
            String databaseName,
            String username,
            String password,
            String connectionUrl,
            String expectedErrorMessage,
            @Description TestCaseDescription testCaseDescription) {
        String generatedKey = getNameGenerator().getRandomNameForResource();
        testContext
                .withDescription(testCaseDescription)
                .given(DatabaseEntity.class)
                .withName(databaseName)
                .withConnectionUserName(username)
                .withConnectionPassword(password)
                .withConnectionURL(connectionUrl)
                .when(DatabaseEntity.post(), key(generatedKey))
                .expect(BadRequestException.class,
                        expectedMessage(expectedErrorMessage)
                                .withKey(generatedKey))
                .validate();
    }

    @Test(dataProvider = INVALID_ATTRIBUTE_PROVIDER)
    public void testDatabaseTestConnectionWithInvalidAttribute(
            TestContext testContext,
            String databaseName,
            String username,
            String password,
            String connectionUrl,
            String expectedErrorMessage,
            @Description TestCaseDescription testCaseDescription) {
        String generatedKey = getNameGenerator().getRandomNameForResource();

        testContext
                .withDescription(testCaseDescription)
                .given(DatabaseTestEntity.class)
                .withRequest(new DatabaseV4Request())
                .withName(databaseName)
                .withConnectionUserName(username)
                .withConnectionPassword(password)
                .withConnectionURL(connectionUrl)
                .withType("HIVE")
                .when(DatabaseTestEntity.testConnection(), key(generatedKey))
                .expect(BadRequestException.class,
                        expectedMessage(expectedErrorMessage)
                                .withKey(generatedKey))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "Database test with non existing name",
            when = "calling test Database",
            then = "the test connection should throw access denied")
    public void testDatabaseTestConnectionWithNonExistingDbName(TestContext testContext) {
        String generatedKey = getNameGenerator().getRandomNameForResource();

        testContext
                .given(DatabaseTestEntity.class)
                .withExistingName("aNonExistingDb")
                .when(DatabaseTestEntity.testConnection(), key(generatedKey))
                .then(DatabaseTestAccessDeniedAssertion.getAssertion(), key(generatedKey))
                .validate();
    }

    @DataProvider(name = DB_TYPE_PROVIDER, parallel = true)
    public Object[][] provideTypes() {
        List<DatabaseType> databaseTypeList = Arrays.asList(DatabaseType.values());
        Object[][] objects = new Object[databaseTypeList.size()][2];
        databaseTypeList
                .forEach(databaseType -> {
                    objects[databaseTypeList.indexOf(databaseType)][0] = getBean(TEST_CONTEXT_CLASS);
                    objects[databaseTypeList.indexOf(databaseType)][1] = databaseType;
                    objects[databaseTypeList.indexOf(databaseType)][2] =
                            new TestCaseDescription.TestCaseDescriptionBuilder()
                            .given("Testing databaseType with" + databaseType + " type")
                            .when("calling create endpoint")
                            .then("returns successful creation");
                });
        return objects;
    }

    @DataProvider(name = INVALID_ATTRIBUTE_PROVIDER, parallel = true)
    public Object[][] provideInvalidAttributes() {
        return new Object[][]{
                {
                        getBean(TEST_CONTEXT_CLASS),
                        longStringGeneratorUtil.stringGenerator(51),
                        DATABASE_USERNAME, DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "The length of the name has to be in range of 4 to 50",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("Testing database with too long name")
                                .when("calling create endpoint")
                                .then("returns with BadRequestException")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        "abc",
                        DATABASE_USERNAME,
                        DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "The length of the name has to be in range of 4 to 50",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("Testing database with too short name")
                                .when("calling create endpoint")
                                .then("returns with BadRequestException")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        "a-@#$%|:&*;",
                        DATABASE_USERNAME,
                        DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "The database's name can only contain lowercase alphanumeric characters and "
                                + "hyphens and has start with an alphanumeric character",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("Testing database with mixed characters name")
                                .when("calling create endpoint")
                                .then("returns with BadRequestException")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        getNameGenerator().getRandomNameForResource(),
                        null,
                        DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "connectionUserName: null, error: must not be null",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("Testing database with 'null' connectionUserName")
                                .when("calling create endpoint")
                                .then("returns with BadRequestException")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        getNameGenerator().getRandomNameForResource(),
                        DATABASE_USERNAME,
                        null,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "connectionPassword: null, error: must not be null",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("Testing database with 'null' password")
                                .when("calling create endpoint")
                                .then("returns with BadRequestException")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        getNameGenerator().getRandomNameForResource(),
                        DATABASE_USERNAME,
                        DATABASE_PASSWORD,
                        DATABASE_HOST_PORT_DB,
                        "Unsupported database type",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("Testing database with unsupported database type")
                                .when("calling create endpoint")
                                .then("returns with BadRequestException")
                }
        };
    }
}
