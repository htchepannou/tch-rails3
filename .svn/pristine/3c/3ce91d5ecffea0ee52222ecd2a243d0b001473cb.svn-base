/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.annotation.Job;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.exception.JobException;
import com.tchepannou.rails.core.service.JobService;
import com.tchepannou.rails.core.service.JobService.JobExecutor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link ActionControllerProvider}.
 *
 * @author herve
 */
public class JobControllerContainer
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (JobControllerContainer.class);
    
    //-- Attribute
    private ContainerContext _containerContext;
    private List<JobService.JobInfo> _jobs = new ArrayList<JobService.JobInfo> ();
    private boolean _initialized;

    //-- Constructeur
    public JobControllerContainer ()
    {
    }

    //-- Public method
    public void init (ContainerContext containerContext)
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("init(" + containerContext + ")");
        }
        LOG.info ("Initializing");
        _containerContext = containerContext;
        try
        {
            loadJobControllers (_containerContext.getBasePackage () + ".job");
            initJobs ();
            _initialized = true;

            LOG.info ("Initialized");
        }
        catch ( ClassNotFoundException e )
        {
            throw new InitializationException ("Unable to initialized the JobControllerContainer", e);
        }
        catch ( IOException e )
        {
            throw new InitializationException ("Unable to initialized the JobControllerContainer", e);
        }
        catch ( JobException e )
        {
            throw new InitializationException ("Unable to initialized the JobControllerContainer", e);
        }
    }

    public void destroy ()
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("destroy()");
        }

        LOG.info ("Destroying");
        _jobs.clear ();
        _initialized = false;
        LOG.info ("Destroyed");
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    public void register (Class<? extends JobController> clazz)
    {
        for ( Method method: clazz.getMethods () )
        {
            if ( method.getAnnotation (Job.class) != null )
            {
                int modifier = method.getModifiers ();
                Object[] params = method.getTypeParameters ();
                if ( Modifier.isPublic (modifier)
                    && !Modifier.isAbstract (modifier)
                    && JobController.class.isAssignableFrom (clazz)
                    && (params == null || params.length == 0) )
                {
                    registerJob (clazz, method);
                }
            }
        }
    }


    public List<JobService.JobInfo> getJobs ()
    {
        return _jobs;
    }

    public JobService.JobInfo getJob (String path)
    {
        String name = path2name(path);
        for (JobService.JobInfo info : getJobs ())
        {
            Method m = info.getMethod ();
            String fullname = m.getDeclaringClass ().getName () + "." + m.getName ();
            if (fullname.endsWith (name))
            {
                return info;
            }
        }
        return null;
    }



    //-- Private
    private String path2name (String path)
    {
        if (path.startsWith ("/"))
        {
            path = path.substring(1);
        }
        String[] tokens = path.split ("/");
        if (tokens.length == 2)
        {
            String name = tokens[0];
            String method = tokens[1];

            return name.substring (0, 1).toUpperCase () + name.substring (1) + JobController.CLASSNAME_SUFFIX + "." + method;
        }
        else
        {
            return null;
        }
    }

    private void loadJobControllers (String packageName)
        throws ClassNotFoundException,
               IOException
    {
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Loading JobControllers classes from package " + packageName);
        }
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends JobController>> classes = reflections.getSubTypesOf (JobController.class);
        for (Class<? extends JobController> clazz : classes)
        {
            int modifier = clazz.getModifiers ();
            if ( Modifier.isPublic (modifier) && !Modifier.isAbstract (modifier))
            {
                register (clazz);
            }
        }
    }

    private void registerJob (final Class<? extends JobController> clazz, final Method method)
    {
        Job job = method.getAnnotation (Job.class);
        if ( job == null )
        {
            return;
        }
        final String expr = job.cron ();
        LOG.info ("Registering job : " + method.getClass ().getName () + "." + method.getName () + "/" + expr);

        JobService.JobInfo info = new JobService.JobInfo ()
        {
            public String getCronExpression ()
            {
                return expr;
            }

            public Class<? extends JobController> getControllerClass ()
            {
                return clazz;
            }

            public Method getMethod ()
            {
                return method;
            }

            public ContainerContext getContainerContext ()
            {
                return _containerContext;
            }

            public JobExecutor getExecutor ()
            {
                return new JobControllerWrapper ();
            }
        };
        _jobs.add (info);
    }

    private void initJobs ()
        throws JobException
    {
        JobService js = ( JobService ) _containerContext.findService (JobService.class);
        if (js != null)
        {
            for ( JobService.JobInfo job: _jobs )
            {
                js.schedule (job);
            }
        }
        else
        {
            LOG.warn ("No JobService available. none of the job will be schedules");
        }
    }
}
