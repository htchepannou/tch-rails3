/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.Service;
import java.util.List;

/**
 * This is the service for managing the system configuration.
 *
 * @author herve
 */
public interface OptionService
    extends Service
{
    //-- Static Attributes
    public static final String OPTION_SITE_NAME = "site.name";
    public static final String OPTION_SITE_URL = "site.url";
    public static final String OPTION_SITE_TITLE = "site.title";
    public static final String OPTION_SITE_DESCRIPTION = "site.description";
    public static final String OPTION_SITE_KEYWORDS = "site.keywords";
    public static final String OPTION_SITE_LOCALE = "site.locale";    
    public static final String OPTION_ASSET_URL = "asset.url";
    public static final String OPTION_ASSET_URL_HTTPS = "asset.url.https";
    public static final String OPTION_ASSET_UPLOAD_DIR = "asset.upload.directory";
    public static final String OPTION_ASSET_UPLOAD_MAX_SIZE = "asset.upload.maxsize";
    public static final String OPTION_MAIL_ADMIN_EMAIL = "mail.admin.email";
    public static final String OPTION_MAIL_DEBUG = "mail.debug";
    public static final String OPTION_MAIL_FROM = "mail.from";
    public static final String OPTION_MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String OPTION_MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String OPTION_MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String OPTION_MAIL_SMTP_USER = "mail.smtp.user";
    public static final String OPTION_MAIL_SMTP_PASSWORD = "mail.smtp.password";
    public static final String OPTION_MAIL_SSL = "mail.smtp.ssl";

    public static final boolean DEFAULT_MAIL_DEBUG = true;
    public static final String DEFAULT_MAIL_SMTP_HOST = "localhost";
    public static final int DEFAULT_MAIL_SMTP_PORT = 25;
    public static final long DEFAULT_ASSET_UPLOAD_MAX_SIZE = 1;

    //-- Public methods
    public List<String> getNames ();
    
    public String get (String name, String defaultValue);

    public void set (String name, String value);
}
