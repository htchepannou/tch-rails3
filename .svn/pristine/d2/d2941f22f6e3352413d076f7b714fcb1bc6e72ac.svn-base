/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.siena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import siena.SienaException;
import siena.jdbc.ConnectionManager;

/**
 * Implementation of {link ConnectionManager} with better handling of JNDI based connection
 * @author herve
 */
public class ConnectionManagerImpl
    implements ConnectionManager
{
    //-- Attributes
    private static final Logger LOG = LoggerFactory.getLogger (ConnectionManagerImpl.class);
    
    private String url;
    private String user;
    private String pass;
    private String jndi;
    private DataSource dataSource;
    private ThreadLocal<Connection> currentConnection = new ThreadLocal<Connection> ();

    //-- ConnectionManager overrides
    public void init (Properties p)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("init(" + p + ")");
        }
        
        LOG.info ("Initializing");
        String driver = p.getProperty ("driver");
        this.url = p.getProperty ("url");
        this.user = p.getProperty ("user");
        this.pass = p.getProperty ("password");
        this.jndi = p.getProperty ("jndi");

        if ( jndi == null )
        {
            try
            {
                LOG.info ("Loading " + driver);
                Class.forName (driver);
            }
            catch ( ClassNotFoundException e )
            {
                throw new SienaException ("Error while loading JDBC driver", e);
            }
        }
        else
        {
            LOG.info ("Binding to " + jndi);
            InitialContext ctx;
            try
            {
                ctx = new InitialContext ();
                dataSource = ( DataSource ) ctx.lookup (jndi);
            }
            catch ( NamingException ex )
            {
                throw new SienaException ("Unable to resolve JNDI object: " + jndi, ex);
            }
        }
    }

    public Connection getConnection ()
    {
        Connection c = currentConnection.get ();
        
        try
        {
            /* Make sure the current connection is not closed !!! */
            if (c != null && c.isClosed ())
            {
                currentConnection.remove ();
                c = null;
            }

            /* Get a new connection */
            if (c == null)
            {
                if ( dataSource != null )
                {
                    c = dataSource.getConnection ();
                }
                else
                {
                    if ( c == null )
                    {
                        c = DriverManager.getConnection (url, user, pass);
                    }
                }
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug("opening connection#" + c.hashCode ());
                }
                currentConnection.set (c);
            }

            return c;
        }
        catch (SQLException e )
        {
            LOG.error ("Unable to open the connection", e);
            throw new SienaException (e);
        }
    }

    public void closeConnection ()
    {
        try
        {
            Connection c = currentConnection.get ();
            if ( c != null )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug("closing connection#" + c.hashCode ());
                }
                    
                c.close ();
            }
        }
        catch ( SQLException e )
        {
            LOG.error ("Unable to close the connection", e);
            throw new SienaException (e);
        }
        finally
        {
            currentConnection.remove ();
        }
    }

    public void beginTransaction (int isolationLevel)
    {
        try
        {
            Connection c = getConnection ();
            c.setAutoCommit (false);
            c.setTransactionIsolation (isolationLevel);
        }
        catch ( SQLException e )
        {
            LOG.error ("Unable to begin the transaction", e);
            throw new SienaException (e);
        }
    }

    public void commitTransaction ()
    {
        try
        {
            Connection c = getConnection ();
            c.commit ();
        }
        catch ( SQLException e )
        {
            LOG.error ("Unable to commit the transaction", e);
            throw new SienaException (e);
        }
    }

    public void rollbackTransaction ()
    {
        try
        {
            Connection c = getConnection ();
            c.rollback ();
        }
        catch ( SQLException e )
        {
            LOG.error ("Unable to rollback the transaction", e);
            throw new SienaException (e);
        }
    }

    public void beginTransaction ()
    {
        beginTransaction (Connection.TRANSACTION_READ_COMMITTED);
    }
}
