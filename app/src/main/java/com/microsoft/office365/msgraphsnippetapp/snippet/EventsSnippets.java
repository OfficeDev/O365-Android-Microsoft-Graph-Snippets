/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.msgraphsnippetapp.snippet;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.microsoft.office365.msgraphapiservices.MSGraphEventsService;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

import static com.microsoft.office365.msgraphsnippetapp.R.array.create_event;
import static com.microsoft.office365.msgraphsnippetapp.R.array.delete_event;
import static com.microsoft.office365.msgraphsnippetapp.R.array.get_user_events;
import static com.microsoft.office365.msgraphsnippetapp.R.array.update_event;

public abstract class EventsSnippets<Result> extends AbstractSnippet<MSGraphEventsService, Result> {

    public EventsSnippets(Integer descriptionArray) {
        super(SnippetCategory.eventsSnippetCategory, descriptionArray);
    }

    static EventsSnippets[] getEventsSnippets() {
        return new EventsSnippets[]{
                // Marker element
                new EventsSnippets(null) {

                    @Override
                    public void request(MSGraphEventsService o, Callback callback) {
                        //No implementation
                    }
                },
                //Snippets

                /*
                 * Get all events for the signed in user.
                 * HTTP GET https://graph.microsoft.com/{version}/me/events
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/api/user_list_events
                 */
                new EventsSnippets<Void>(get_user_events) {

                    @Override
                    public void request(
                            MSGraphEventsService MSGraphEventsService,
                            retrofit.Callback<Void> callback) {
                                 MSGraphEventsService.getEvents(getVersion(), callback);
                             }
                },

                /*
                 * Adds an event to the signed-in user\'s calendar.
                 * HTTP POST https://graph.microsoft.com/{version}/me/events
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/api/user_post_events
                 */
                new EventsSnippets<Void>(create_event) {

                    @Override
                    public void request(
                            MSGraphEventsService MSGraphEventsService,
                            retrofit.Callback<Void> callback) {

                                JsonObject newEvent = createNewEventJsonBody();

                                TypedString body = new TypedString(newEvent.toString()) {
                                    @Override
                                    public String mimeType() {
                                        return "application/json";
                                    }
                                };

                                //Call service to POST the new event
                                MSGraphEventsService.createNewEvent(getVersion(), body, callback);
                            }

                },
                 /*
                 * Update an event
                 * HTTP PATCH https://graph.microsoft.com/{version}/me/events/{Event.Id}
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/api/event_update
                 */
                new EventsSnippets<Void>(update_event) {

                    @Override
                    public void request(
                            final MSGraphEventsService MSGraphEventsService,
                            final retrofit.Callback<Void> callback) {
                        final JsonObject newEvent = createNewEventJsonBody();
                        TypedString body = new TypedString(newEvent.toString()) {
                            @Override
                            public String mimeType() {
                                return "application/json";
                            }
                        };
                        MSGraphEventsService.createNewEvent(getVersion(), body, new Callback<Void>() {

                            @Override
                            public void success(Void aVoid, Response response) {
                                String groupID = getGroupId(response);

                                //update the group we created
                                JsonObject updateEvent = newEvent;
                                updateEvent.remove("Subject");
                                updateEvent.addProperty("Subject", "Sync of the Week");

                                TypedString updateBody = new TypedString(updateEvent.toString()) {
                                    @Override
                                    public String mimeType() {
                                        return "application/json";
                                    }
                                };
                                MSGraphEventsService.updateEvent(
                                        getVersion(),
                                        groupID,
                                        updateBody,
                                        callback);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                //pass along error to original callback
                                callback.failure(error);
                            }
                        });
                    }

                },
                 /*
                 * Delete an event
                 * HTTP DELETE https://graph.microsoft.com/{version}/me/events/{Event.Id}
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/api/event_delete
                 */
                new EventsSnippets<Void>(delete_event) {

                    @Override
                    public void request(
                        final MSGraphEventsService MSGraphEventsService,
                        final retrofit.Callback<Void> callback) {
                                final JsonObject newEvent = createNewEventJsonBody();
                                TypedString body = new TypedString(newEvent.toString()) {
                                    @Override
                                    public String mimeType() {
                                        return "application/json";
                                    }
                                };
                                MSGraphEventsService.createNewEvent(getVersion(), body, new Callback<Void>() {

                                    @Override
                                    public void success(Void aVoid, Response response) {
                                        //Delete the event we created
                                        MSGraphEventsService.deleteEvent(
                                                getVersion(),
                                                getGroupId(response),
                                                callback);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        //pass along error to original callback
                                        callback.failure(error);
                                    }
                             });
                    }
                }

        };
    }

    @NonNull
    private static JsonObject createNewEventJsonBody() {
        //Set start time to now and end in 1 hour
        DateTime start = new DateTime().now();
        DateTime end = start.plusHours(1);

        //create body
        JsonObject newEvent = new JsonObject();
        newEvent.addProperty("Subject", "Office 365 unified API discussion");

        JsonObject startDate = new JsonObject();
        startDate.addProperty("DateTime",start.toString());
        startDate.addProperty("TimeZone", "UTC");
        newEvent.add("Start", startDate);

        JsonObject endDate = new JsonObject();
        endDate.addProperty("DateTime", end.toString());
        endDate.addProperty("TimeZone","UTC");
        newEvent.add("End", endDate);

        //create location
        JsonObject location = new JsonObject();
        location.addProperty("DisplayName", "Bill's office");
        newEvent.add("Location", location);

        //create attendees array with one attendee
        //start with attendee
        JsonObject attendee = new JsonObject();
        attendee.addProperty("Type", "Required");
        JsonObject emailaddress = new JsonObject();
        emailaddress.addProperty("Address", "mara@fabrikam.com");
        attendee.add("EmailAddress", emailaddress);

        //now create attendees array
        JsonArray attendees = new JsonArray();
        attendees.add(attendee);
        newEvent.add("Attendees", attendees);

        //create email body
        JsonObject emailBody = new JsonObject();
        emailBody.addProperty("Content", "Let's discuss the power of the Office 365 unified API.");
        emailBody.addProperty("ContentType", "Text");
        newEvent.add("Body", emailBody);
        return newEvent;
    }

    public abstract void request(MSGraphEventsService MSGraphEventsService, Callback<Result> callback);

    /**
     * Gets the group object id from the HTTP response object
     * returned from a group REST call. Method expects that the JSON is a single
     * group object.
     *
     * @param json The JSON to parse. Expected to be a single group object
     * @return The group id (objectID) of the first group found in the array.
     */
    protected String getGroupId(retrofit.client.Response json) {
        if (json == null)
            return "";

        String groupID;

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(json.getBody().in(), "UTF-8"));
            JsonElement responseElement = new JsonParser().parse(reader);
            JsonObject responseObject = responseElement.getAsJsonObject();
            groupID = responseObject.get("Id").getAsString();
            return groupID;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}