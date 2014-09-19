package io.oasp.module.security.common.impl.accesscontrol;

import io.oasp.module.security.common.api.accesscontrol.AccessControl;
import io.oasp.module.security.common.api.accesscontrol.AccessControlGroup;
import io.oasp.module.security.common.api.accesscontrol.AccessControlPermission;
import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.api.accesscontrol.AccessControlSchema;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * This is the test-case for {@link AccessControlSchema} and {@link AccessControlSchemaXmlMapper}.
 *
 * @author hohwille
 */
public class AccessControlSchemaTest extends Assert {

  /** The location of the reference configuration for regression tests. */
  private static final String SCHEMA_XML = "config/app/security/access-control-schema.xml";

  /** The location of the configuration with a cyclic dependency. */
  private static final String SCHEMA_XML_CYCLIC = "config/app/security/access-control-schema_cyclic.xml";

  /** The location of the configuration that is syntactically corrupted (invalid group reference). */
  private static final String SCHEMA_XML_CORRUPTED = "config/app/security/access-control-schema_corrupted.xml";

  /** The location of the configuration that is syntactically corrupted (invalid group reference). */
  private static final String SCHEMA_XML_ILLEGAL = "config/app/security/access-control-schema_illegal.xml";

  /**
   * The constructor.
   */
  public AccessControlSchemaTest() {

    super();
  }

  /**
   * Regression test for {@link AccessControlSchemaXmlMapper#write(AccessControlSchema, java.io.OutputStream)}.
   *
   * @throws Exception if something goes wrong.
   */
  @Test
  public void testWriteXml() throws Exception {

    // given
    AccessControlSchema conf = createSecurityConfiguration();
    String expectedXml = readSecurityConfigurationXmlFile();
    // when
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    new AccessControlSchemaXmlMapper().write(conf, baos);
    String actualXml = baos.toString();
    // then
    assertEquals(expectedXml.replaceAll("\\r *|\\n *", ""), actualXml);
  }

  /**
   * Regression test for {@link AccessControlSchemaXmlMapper#read(InputStream)}.
   *
   * @throws Exception if something goes wrong.
   */
  @Test
  public void testReadXml() throws Exception {

    // given
    AccessControlSchema expectedConf = createSecurityConfiguration();
    // when
    ClassPathResource resource = new ClassPathResource(SCHEMA_XML);
    AccessControlSchema actualConf;
    try (InputStream in = resource.getInputStream()) {
      actualConf = new AccessControlSchemaXmlMapper().read(in);
    }
    // then
    assertEquals(expectedConf, actualConf);
  }

  /**
   * Tests that {@link AccessControlProviderImpl} properly detects cyclic inheritance of {@link AccessControlGroup}s.
   */
  @Test
  public void testProviderCyclic() {

    try {
      createProvider(SCHEMA_XML_CYCLIC);
      fail("Exception expected!");
    } catch (IllegalStateException e) {
      assertTrue(e.getMessage().startsWith("Cyclic inheritance "));
    }
  }

  /**
   * Tests that {@link AccessControlProviderImpl} with corrupted XML (not well-formed).
   */
  @Test
  public void testProviderCorrupted() {

    try {
      createProvider(SCHEMA_XML_CORRUPTED);
      fail("Exception expected!");
    } catch (IllegalStateException e) {
      String message = e.getMessage();
      assertTrue(message, message.contains(SCHEMA_XML_CORRUPTED.toString()));
      String causeMessage = e.getCause().getMessage();
      assertEquals("Unmarshalling XML failed!", causeMessage);
    }
  }

  /**
   * Tests that {@link AccessControlProviderImpl} with illegal XML (undefined group reference).
   */
  @Test
  public void testProviderIllegal() {

    try {
      createProvider(SCHEMA_XML_ILLEGAL);
      fail("Exception expected!");
    } catch (IllegalStateException e) {
      String message = e.getMessage();
      assertTrue(message, message.contains(SCHEMA_XML_ILLEGAL.toString()));
      String causeMessage = e.getCause().getMessage();
      assertTrue(causeMessage, causeMessage.contains("Undefined ID \"Waiter\""));
    }
  }

  /**
   * Tests that {@link AccessControlProviderImpl} properly detects cyclic inheritance of {@link AccessControlGroup}s.
   */
  public void testProvider() {

    AccessControlProvider provider = createProvider(SCHEMA_XML);
    Set<AccessControl> permissions = new HashSet<>();
    boolean success;
    success = provider.collectAccessControls("", permissions);
    assertFalse(success);
    assertEquals(0, permissions.size());
    success = provider.collectAccessControls("Admin", permissions);
    assertTrue(success);
    assertTrue(permissions.contains(provider.getAccessControl("Customer_ReadCustomer")));
    assertTrue(permissions.contains(provider.getAccessControl("Customer_CreateCustomer")));
    assertTrue(permissions.contains(provider.getAccessControl("Customer_DeleteCustomer")));
    assertEquals(24, permissions.size());
    success = provider.collectAccessControls("ReadOnly", permissions);
    assertTrue(success);
    assertTrue(permissions.contains(provider.getAccessControl("Contract_ReadContractAsset")));
    assertTrue(permissions.contains(provider.getAccessControl("Contract_UpdateContractAsset")));
    assertFalse(permissions.contains(provider.getAccessControl("System_DeleteUser")));
    assertEquals(5, permissions.size());

  }

  private AccessControlProvider createProvider(String location) {

    ClassPathResource resource = new ClassPathResource(location);
    AccessControlProviderImpl accessControlProvider = new AccessControlProviderImpl();
    AccessControlSchemaProviderImpl accessControlSchemaProvider = new AccessControlSchemaProviderImpl();
    accessControlSchemaProvider.setAccessControlSchema(resource);
    accessControlProvider.setAccessControlSchemaProvider(accessControlSchemaProvider);
    accessControlProvider.initialize();
    return accessControlProvider;
  }

  private String readSecurityConfigurationXmlFile() throws IOException, UnsupportedEncodingException {

    ClassPathResource resource = new ClassPathResource(SCHEMA_XML);
    byte[] data = Files.readAllBytes(Paths.get(resource.getURI()));
    String expectedXml = new String(data, "UTF-8");
    return expectedXml;
  }

  private AccessControlSchema createSecurityConfiguration() {

    AccessControlSchema conf = new AccessControlSchema();
    AccessControlGroup readOnly = new AccessControlGroup("ReadOnly");
    readOnly.getPermissions().add(new AccessControlPermission("Customer_ReadCustomer"));
    readOnly.getPermissions().add(new AccessControlPermission("Customer_ReadProfile"));
    readOnly.getPermissions().add(new AccessControlPermission("Customer_ReadAddress"));
    readOnly.getPermissions().add(new AccessControlPermission("Contract_ReadContract"));
    readOnly.getPermissions().add(new AccessControlPermission("Contract_ReadContractAsset"));
    AccessControlGroup readWrite = new AccessControlGroup("ReadWrite");
    readWrite.getInherits().add(readOnly);
    readWrite.getPermissions().add(new AccessControlPermission("Customer_CreateCustomer"));
    readWrite.getPermissions().add(new AccessControlPermission("Customer_CreateProfile"));
    readWrite.getPermissions().add(new AccessControlPermission("Customer_CreateAddress"));
    readWrite.getPermissions().add(new AccessControlPermission("Contract_CreateContract"));
    readWrite.getPermissions().add(new AccessControlPermission("Contract_CreateContractAsset"));
    readWrite.getPermissions().add(new AccessControlPermission("Customer_UpdateCustomer"));
    readWrite.getPermissions().add(new AccessControlPermission("Customer_UpdateProfile"));
    readWrite.getPermissions().add(new AccessControlPermission("Customer_UpdateAddress"));
    readWrite.getPermissions().add(new AccessControlPermission("Contract_UpdateContract"));
    readWrite.getPermissions().add(new AccessControlPermission("Contract_UpdateContractAsset"));
    AccessControlGroup admin = new AccessControlGroup("Admin");
    admin.getInherits().add(readWrite);
    admin.getPermissions().add(new AccessControlPermission("Customer_DeleteCustomer"));
    admin.getPermissions().add(new AccessControlPermission("Customer_DeleteProfile"));
    admin.getPermissions().add(new AccessControlPermission("Customer_DeleteAddress"));
    admin.getPermissions().add(new AccessControlPermission("Contract_DeleteContract"));
    admin.getPermissions().add(new AccessControlPermission("Contract_DeleteContractAsset"));
    admin.getPermissions().add(new AccessControlPermission("System_ReadUser"));
    admin.getPermissions().add(new AccessControlPermission("System_CreateUser"));
    admin.getPermissions().add(new AccessControlPermission("System_UpdateUser"));
    admin.getPermissions().add(new AccessControlPermission("System_DeleteUser"));
    conf.getGroups().add(readOnly);
    conf.getGroups().add(readWrite);
    conf.getGroups().add(admin);
    return conf;
  }

}
