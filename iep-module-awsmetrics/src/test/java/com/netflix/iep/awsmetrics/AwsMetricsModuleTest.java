/*
 * Copyright 2014-2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.iep.awsmetrics;

import com.amazonaws.metrics.AwsSdkMetrics;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.netflix.spectator.api.DefaultRegistry;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.aws.SpectatorMetricsCollector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AwsMetricsModuleTest {

  @Test
  public void withRegistryBinding() {
    AwsSdkMetrics.setMetricCollector(null);
    Assert.assertFalse(AwsSdkMetrics.getMetricCollector() instanceof SpectatorMetricsCollector);
    final Registry registry = new DefaultRegistry();
    Guice.createInjector(new AwsMetricsModule(), new AbstractModule() {
      @Override protected void configure() {
        bind(Registry.class).toInstance(registry);
      }
    });
    Assert.assertTrue(AwsSdkMetrics.getMetricCollector() instanceof SpectatorMetricsCollector);
  }

  @Test
  public void withoutRegistryBinding() {
    AwsSdkMetrics.setMetricCollector(null);
    Assert.assertFalse(AwsSdkMetrics.getMetricCollector() instanceof SpectatorMetricsCollector);
    Guice.createInjector(new AwsMetricsModule());
    Assert.assertTrue(AwsSdkMetrics.getMetricCollector() instanceof SpectatorMetricsCollector);
  }

}