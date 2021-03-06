/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.msgraphsnippetapp;

import com.microsoft.office365.auth.AzureADModule;
import com.microsoft.office365.auth.AzureAppCompatActivity;
import com.microsoft.office365.msgraphsnippetapp.application.SnippetApp;
import com.microsoft.office365.msgraphsnippetapp.inject.AzureModule;
import com.microsoft.office365.msgraphsnippetapp.inject.ObjectGraphInjector;

import dagger.ObjectGraph;

public abstract class BaseActivity
        extends AzureAppCompatActivity
        implements ObjectGraphInjector {

    @Override
    protected AzureADModule getAzureADModule() {
        AzureADModule.Builder builder = new AzureADModule.Builder(this);
        builder.validateAuthority(true)
                .authorityUrl(ServiceConstants.AUTHORITY_URL)
                .clientId(ServiceConstants.CLIENT_ID)
                .scopes(ServiceConstants.SCOPES);
        return builder.build();
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new AzureModule()};
    }

    @Override
    protected ObjectGraph getRootGraph() {
        return SnippetApp.getApp().mObjectGraph;
    }

    @Override
    public void inject(Object target) {
        mObjectGraph.inject(target);
    }
}