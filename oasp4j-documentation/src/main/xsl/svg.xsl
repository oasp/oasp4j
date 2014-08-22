<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xlink="http://www.w3.org/1999/xlink"
				exclude-result-prefixes=""
				version='1.0'>
        
  <xsl:variable name="wiki.url" select="'https://github.com/oasp/oasp4j/wiki/'"/>
  
  <xsl:template match="@xlink:href">
    <xsl:attribute name="{name()}" namespace="{namespace-uri()}"><xsl:choose>
      <xsl:when test="starts-with(., 'https://github.com/oasp/oasp4j/wiki/')">
        <!--<xsl:value-of select="concat('OASP4J.pdf#', substring(., string-length('https://github.com/oasp/oasp4j/wiki/') + 1))"/>-->
        <xsl:value-of select="concat('#', substring(., string-length('https://github.com/oasp/oasp4j/wiki/') + 1))"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="."/>
      </xsl:otherwise>
    </xsl:choose></xsl:attribute>
  </xsl:template>

  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
