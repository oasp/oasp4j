package io.oasp.gastronomy.restaurant.general.common.impl.security;

/**
 * This class is responsible for the security aspects of authentication as well as providing user profile data and the
 * access-controls for authoriziation.
 *
 * @author mbrunnli
 * @author hohwille
 * @author agreul
 */
/*
 * TODO restore after security for spring-boot is reenabled
 * https://github.com/oasp/oasp4j/issues/354
 *
 @Named("ApplicationAuthenticationProvider")
 public class ApplicationAuthenticationProvider extends
 AbstractAccessControlBasedAuthenticationProvider<UserData, UserProfile> {


 private static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthenticationProvider.class);

 private Usermanagement usermanagement;


 public ApplicationAuthenticationProvider() {

 super();
 }


 @Inject
 public void setUsermanagement(Usermanagement usermanagement) {

 this.usermanagement = usermanagement;
 if (usermanagement instanceof StaffmanagementImpl) {
 List<StaffMemberEto> staffmembers = ((StaffmanagementImpl) usermanagement).findAllStaffMembers();
 for (StaffMemberEto staffmember : staffmembers) {
 System.out.println(staffmember.getName());
 }
 }
 }

 @Override
 protected UserProfile retrievePrincipal(String username, UsernamePasswordAuthenticationToken authentication) {

 try {
 return this.usermanagement.findUserProfileByLogin(username);
 } catch (RuntimeException e) {
 e.printStackTrace();
 UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
 LOG.warn("Failed to get user {}.", username, exception);
 throw exception;
 }
 }

 @Override
 protected UserData createUser(String username, String password, UserProfile principal,
 Set<GrantedAuthority> authorities) {

 UserData user = new UserData(username, password, authorities);
 user.setUserProfile(principal);
 return user;
 }

 }*/
