package com.freelance.ahmed.bakingapp.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ahmed on 3/18/2018.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
