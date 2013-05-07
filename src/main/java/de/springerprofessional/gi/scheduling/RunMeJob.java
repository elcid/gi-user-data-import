package de.springerprofessional.gi.scheduling;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RunMeJob extends QuartzJobBean {
    private RunMeTask runMeTask;
    private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

    public void setRunMeTask(RunMeTask runMeTask) {
        this.runMeTask = runMeTask;
    }

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            ApplicationContext appCtx = getApplicationContext(context);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        runMeTask.printMe();

    }

    private ApplicationContext getApplicationContext(JobExecutionContext context)
            throws Exception {
        ApplicationContext appCtx;
        appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
        if (appCtx == null) {
            throw new JobExecutionException(
                    "No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx;
    }
}