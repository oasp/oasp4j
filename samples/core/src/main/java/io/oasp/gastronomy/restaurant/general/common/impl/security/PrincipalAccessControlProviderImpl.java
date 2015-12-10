package io.oasp.gastronomy.restaurant.general.common.impl.security;

/**
 * The implementation of {@link PrincipalAccessControlProvider} for this sample application.<br/>
 * ATTENTION:<br/>
 * In reality you would typically receive the user-profile from the central identity-management (via LDAP) and the roles
 * (and groups) from a central access manager (that might also implement the identify-management). This design was only
 * chosen to keep our sample application simple. Otherwise one would have to start a separate external server
 * application to make everything work what would be too complicated to get things running easily.
 *
 * @author hohwille
 */
/*
 * TODO restore after security for spring-boot is reenabled:
 * https://github.com/oasp/oasp4j/issues/354
 @Named
 public class PrincipalAccessControlProviderImpl implements PrincipalAccessControlProvider<UserProfile> {

 public PrincipalAccessControlProviderImpl() {

 super();
 }

 @Override
 public Collection<String> getAccessControlIds(UserProfile principal) {

 return Arrays.asList(principal.getRole().getName());
 }

 }
 */
