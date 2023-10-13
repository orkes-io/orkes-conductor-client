/*
 * Copyright 2023 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.sdk.examples;

import io.orkes.conductor.client.HumanTaskClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.model.HTScrollableSearchResultHumanTaskEntry;
import io.orkes.conductor.client.model.HumanTaskEntry;
import io.orkes.conductor.client.model.HumanTaskSearchRequest;

import java.util.List;

/**
 * Examples for human task operations in Conductor
 *
 * <p>1. Search human tasks *
 */
public class HumanTaskExample {

    private static HumanTaskClient humanTaskClient;

    public static void main(String[] args) {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        humanTaskClient = orkesClients.getHumanTaskClient();
        HTScrollableSearchResultHumanTaskEntry searchResults = humanTaskClient.searchV2(buildSearchRequest());
        List<HumanTaskEntry> entries = searchResults.getResults();
        System.out.println("Entries count : " + entries.size());
        for (final HumanTaskEntry entry : entries) {
            System.out.println("Human task fetched with id : " + entry.getTaskId());
        }
    }

    private static HumanTaskSearchRequest buildSearchRequest() {
        HumanTaskSearchRequest searchRequest = new HumanTaskSearchRequest();
        searchRequest.setStates(List.of(HumanTaskSearchRequest.StatesEnum.ASSIGNED,
                HumanTaskSearchRequest.StatesEnum.IN_PROGRESS));
        searchRequest.setSortBy("updated_on");
        searchRequest.setResultsLimit(30);
        return searchRequest;
    }
}
