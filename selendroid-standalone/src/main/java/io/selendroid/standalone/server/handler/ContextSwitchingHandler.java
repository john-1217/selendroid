/*
 * Copyright 2015 eBay Software Foundation and selendroid committers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.selendroid.standalone.server.handler;

import io.selendroid.common.device.DeviceTargetPlatform;
import io.selendroid.server.common.Response;
import io.selendroid.server.common.http.HttpRequest;
import io.selendroid.standalone.server.BaseSelendroidStandaloneHandler;
import io.selendroid.standalone.server.model.ActiveSession;
import org.json.JSONException;
import org.json.JSONObject;

public class ContextSwitchingHandler extends BaseSelendroidStandaloneHandler {

  private BaseSelendroidStandaloneHandler proxyHandler;

  public ContextSwitchingHandler(String mappedUri, BaseSelendroidStandaloneHandler proxyHandler) {
    super(mappedUri);
    this.proxyHandler = proxyHandler;
  }

  @Override
  protected Response handleRequest(HttpRequest request, JSONObject payload) throws JSONException {
    ActiveSession session = getActiveSession(request);
    if (new DeviceTargetPlatform.DeviceTargetPlatformComparator().compare(session.getDevice().getTargetPlatform(), 
        DeviceTargetPlatform.ANDROID19) >= 0) {
      return null;
    } else {
      return proxyHandler.handle(request);
    }
  }
}
