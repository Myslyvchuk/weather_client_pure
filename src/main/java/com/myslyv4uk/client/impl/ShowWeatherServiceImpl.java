package com.myslyv4uk.client.impl;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myslyv4uk.client.api.ShowWeatherService;
import com.myslyv4uk.weather.api.WeatherService;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShowWeatherServiceImpl extends TimerTask implements ShowWeatherService {

  private int start;
  private int end;
  public volatile WeatherService weatherService;

  private static final Logger logger = LoggerFactory.getLogger(ShowWeatherServiceImpl.class);

  public ShowWeatherServiceImpl(WeatherService weatherService) {
    this.weatherService = weatherService;
    setStart(20);
    setEnd(50);
  }

  public synchronized void transmitTemperature(int from, int to) {
    logger.info("" + weatherService.getCurrentTemperaturePure(from, to));
  }

  @Override
  public void run() {
    transmitTemperature(start, end);
  }

}
