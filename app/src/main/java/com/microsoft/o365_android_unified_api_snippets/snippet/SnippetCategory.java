/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/
package com.microsoft.o365_android_unified_api_snippets.snippet;

import com.microsoft.o365_android_unified_api_snippets.application.SnippetApp;
import com.microsoft.unifiedapi.service.UnifiedContactService;
import com.microsoft.unifiedapi.service.UnifiedEventsService;
import com.microsoft.unifiedapi.service.UnifiedGroupsService;
import com.microsoft.unifiedapi.service.UnifiedMailService;
import com.microsoft.unifiedapi.service.UnifiedMeService;
import com.microsoft.unifiedapi.service.UnifiedUserService;

import static com.microsoft.o365_android_unified_api_snippets.R.string.section_contacts;
import static com.microsoft.o365_android_unified_api_snippets.R.string.section_events;
import static com.microsoft.o365_android_unified_api_snippets.R.string.section_groups;
import static com.microsoft.o365_android_unified_api_snippets.R.string.section_me;
import static com.microsoft.o365_android_unified_api_snippets.R.string.section_messages;
import static com.microsoft.o365_android_unified_api_snippets.R.string.section_user;

public class SnippetCategory<T> {
    static final SnippetCategory<UnifiedContactService> contactSnippetCategory
            = new SnippetCategory<>(section_contacts, create(UnifiedContactService.class));
    static final SnippetCategory<UnifiedEventsService> eventsSnippetCategory
            = new SnippetCategory<>(section_events, create(UnifiedEventsService.class));


    static final SnippetCategory<UnifiedGroupsService> groupSnippetCategory
            = new SnippetCategory<>(section_groups, create(UnifiedGroupsService.class));

    static final SnippetCategory<UnifiedUserService> userSnippetCategory
            = new SnippetCategory<>(section_user, create(UnifiedUserService.class));

    static final SnippetCategory<UnifiedMailService> mailSnippetCategory
            = new SnippetCategory<>(section_messages, create(UnifiedMailService.class));

    static final SnippetCategory<UnifiedMeService> meSnippetCategory
            = new SnippetCategory<>(section_me, create(UnifiedMeService.class));

    final String mSection;
    final T mService;

    SnippetCategory(int sectionId, T service) {
        mSection = SnippetApp.getApp().getString(sectionId);
        mService = service;
    }

    private static <T> T create(Class<T> clazz) {
        return SnippetApp.getApp().getRestAdapter().create(clazz);
    }
}
// *********************************************************
//
// O365-Android-Unified-API-Snippets, https://github.com/OfficeDev/O365-Android-Unified-API-Snippets
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// *********************************************************