package com.myslyv4uk.client.provider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myslyv4uk.client.impl.ShowWeatherServiceImpl;
import com.myslyv4uk.weather.api.WeatherService;

public class ShowWeatherStarter implements BundleActivator {
  private ShowWeatherServiceImpl showWeatherServiceImpl;
  private static final Logger logger = LoggerFactory.getLogger(ShowWeatherStarter.class);
  public ScheduledExecutorService threadService;

  @Override
  public void start(BundleContext context) throws Exception {
    logger.info("Service ShowWeatherStarter started");
    ServiceReference reference = context.getServiceReference(WeatherService.class.getName());

    showWeatherServiceImpl =
        new ShowWeatherServiceImpl((WeatherService) context.getService(reference));
    threadService = Executors.newScheduledThreadPool(1);
    threadService.scheduleAtFixedRate(showWeatherServiceImpl, 0, 1, TimeUnit.SECONDS);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    logger.info("Service ShowWeatherStarter stopped");
    if (threadService != null) {
      threadService.shutdownNow();
    }
  }

}
