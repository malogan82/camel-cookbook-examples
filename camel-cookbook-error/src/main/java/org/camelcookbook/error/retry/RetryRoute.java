/*
 * Copyright (C) Scott Cranton, Jakub Korab, and Christian Posta
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.error.retry;

import org.apache.camel.builder.RouteBuilder;
import org.camelcookbook.error.shared.SporadicProcessor;

public class RetryRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler().maximumRedeliveries(2));

        from("direct:start")
            .bean(SporadicProcessor.class)
            .to("mock:result");

        from("direct:routeSpecific")
            .errorHandler(defaultErrorHandler().maximumRedeliveries(2))
            .bean(SporadicProcessor.class)
            .to("mock:result");

        from("direct:routeSpecificDelay")
            .errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(500))
            .bean(SporadicProcessor.class)
            .to("mock:result");
    }
}