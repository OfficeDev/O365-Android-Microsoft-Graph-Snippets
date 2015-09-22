/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/
package com.microsoft.o365_android_unified_api_snippets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microsoft.o365_android_unified_api_snippets.snippet.AbstractSnippet;
import com.microsoft.o365_android_unified_api_snippets.snippet.SnippetContent;

public class SnippetListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    @Override
    public int getCount() {
        return SnippetContent.ITEMS.size();
    }

    @Override
    public AbstractSnippet<?, ?> getItem(int position) {
        return (AbstractSnippet) SnippetContent.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return null != getItem(position).getDescription();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == mContext) {
            mContext = parent.getContext();
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        AbstractSnippet<?, ?> clickedSnippet = getItem(position);
        boolean isSegment = (null == clickedSnippet.getDescription());

        final int id = isSegment ? R.layout.list_segment : R.layout.list_element;
        if (null == convertView || isWrongViewType(isSegment, convertView)) {
            convertView = mLayoutInflater.inflate(id, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.txt_snippet_name);
        name.setText(clickedSnippet.getName());

        //Set text to indicate if Admin account is required to run the snippet
        if (!isSegment) {
            TextView adminIndicator = (TextView) convertView.findViewById(R.id.admin_indicator);
            if (adminIndicator != null) {
                if (clickedSnippet.getIsAdminRequiredAdmin()) {
                    //Admin account required
                    adminIndicator.setText(R.string.admin);
                    adminIndicator.setVisibility(View.VISIBLE);
                } else {
                    //Admin account not required
                    adminIndicator.setVisibility(View.GONE);
                }
            }
        }

        return convertView;
    }

    private boolean isWrongViewType(boolean isSegment, View convertView) {
        View v = convertView.findViewById(R.id.admin_indicator);
        return !isSegment && null == v || (isSegment && null != v);
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