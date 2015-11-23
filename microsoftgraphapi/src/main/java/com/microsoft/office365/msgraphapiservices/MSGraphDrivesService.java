/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.msgraphapiservices;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedString;

public interface MSGraphDrivesService {

    /**
     *
     * @param version The version of the API to use (beta, v1, etc...)
     * @param callback will be called with results of REST operation
     */
    @GET("/{version}/me/drive")
    void getDrive(
            @Path("version") String version,
            Callback<Void> callback
    );

    /**
     *
     * @param version The version of the API to use (beta, v1, etc...)
     * @param callback will be called with results of REST operation
     */
    @GET("/{version}/myOrganization/drives")
    void getOrganizationDrives(
            @Path("version") String version,
            Callback<Void> callback
    );

    /**
     * Gets children file metadata of the root folder
     *
     * @param version The version of the API to use (beta, v1, etc...)
     * @param callback will be called with results of REST operation
     */
    @GET("/{version}/me/drive/root/children")
    void getCurrentUserFiles(
            @Path("version") String version,
            Callback<Void> callback
    );

    /**
     * Creates a new file under the root folder
     *
     * @param version
     * @param filename
     * @param value
     * @param callback
     */
    @PUT("/{version}/me/drive/root/children/{filename}/content")
    void putNewFile(
            @Path("version") String version,
            @Path("filename") String filename,
            @Body TypedString value,
            Callback<Void> callback
    );

    /**
     * Downloads the content of a file from a user root folder
     * @param version
     * @param filename
     * @param callback
     */
    @GET("/{version}/me/drive/items/{filename}/content")
    void downloadFile(
            @Path("version") String version,
            @Path("filename") String filename,
            Callback<Void> callback
    );

    /**
     * Updates the contents of a file owned by the signed in user
     * @param version
     * @param fileId
     * @param value
     * @param callback
     */
    @PUT("/{version}/me/drive/items/{fileId}/content")
    void updateFile(
            @Path("version") String version,
            @Path("fileId") String fileId,
            @Body TypedString value,
            Callback<Void> callback
    );

    /**
     * Deletes a file by file id
     * @param version
     * @param fileId
     * @param callback
     */
    @DELETE("/{version}/me/drive/items/{fileId}/")
    void deleteFile(
            @Path("version") String version,
            @Path("fileId") String fileId,
            Callback<Void> callback
    );

    /**
     * Rename the specified file
     * @param version
     * @param fileId
     * @param body
     * @param callback
     */
    @PATCH("/{version}/me/drive/items/{fileId}/")
    void renameFile(
            @Path("version") String version,
            @Path("fileId") String fileId,
            @Body TypedString body,
            Callback<Void> callback
    );

    /**
     * Create a folder under user root folder
     * @param version
     * @param body
     * @param callback
     */
    @POST("/{version}/me/drive/root/children")
    void createFolder(
            @Path("version") String version,
            @Body TypedString body,
            Callback<Void> callback
    );
}