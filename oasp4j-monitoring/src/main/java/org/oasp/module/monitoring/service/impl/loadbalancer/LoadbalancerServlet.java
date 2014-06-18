package org.oasp.module.monitoring.service.impl.loadbalancer;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet is used to check whether this application is alive and can be used by the load-balancer or if it is
 * currently unavailable (or under maintenance). It returns HTTP OK if the "isAlive file" is present, if not HTTP
 * FORBIDDEN will be returned.
 * 
 * The location of the "isAlive - file" can be configured via the init parameter {@link #PARAM_IS_ALIVE_FILE_LOCATION}.
 * If no parameter is given {@value #DEFAULT_IS_ALIVE_FILE_LOCATION} is used.
 * 
 * @author mvielsac
 */
public class LoadbalancerServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger LOG = LoggerFactory.getLogger(LoadbalancerServlet.class);

  protected static final String PARAM_IS_ALIVE_FILE_LOCATION = "isAliveFileLocation";

  protected static final String DEFAULT_IS_ALIVE_FILE_LOCATION = "/WEB-INF/classes/config/isAlive";

  private File isAliveFile;

  /**
   * 
   * Inits the servlet.
   */
  @Override
  public void init() {

    LOG.info("Initializing the loadbalancer servlet");
    String isAliveFileLocation = getInitParameter(PARAM_IS_ALIVE_FILE_LOCATION);
    if (isAliveFileLocation == null) {
      LOG.debug("No isAlive param given. Using default: " + DEFAULT_IS_ALIVE_FILE_LOCATION);
      isAliveFileLocation = DEFAULT_IS_ALIVE_FILE_LOCATION;
    }

    String realPathToIsAliveFile = getServletContext().getRealPath(isAliveFileLocation);

    this.isAliveFile = new File(realPathToIsAliveFile);
    LOG.info("Using " + this.isAliveFile.getAbsolutePath() + " as isAlive path");
  }

  /**
   * 
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    if (this.isAliveFile.exists()) {
      LOG.debug("IsAlive-file is present, sending HTTP OK.");
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().write("<html><body><h1>Is Alive!</h1></body></html>");
    } else {
      LOG.info("IsAlive-file " + this.isAliveFile.getAbsolutePath() + " does not exist, sending HTTP FORBIDDEN.");
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }
}
