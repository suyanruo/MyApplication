package com.example.myapplication.zendesk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;

import java.util.Set;

import zendesk.support.RequestProvider;
import zendesk.support.RequestUpdates;
import zendesk.support.Support;

public class UpdateZendeskService extends Service {
  private static final String TAG = "UpdateZendeskService";

  public UpdateZendeskService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.e(TAG, "onStartCommand: ");
    checkZendeskUpdate();
    return super.onStartCommand(intent, flags, startId);
  }

  private void checkZendeskUpdate() {
    final int[] index = {0};
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (index[0]++ < 100) {
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          final RequestProvider requestProvider = Support.INSTANCE.provider().requestProvider();

          requestProvider.getUpdatesForDevice(new ZendeskCallback<RequestUpdates>() {
            @Override
            public void onSuccess(RequestUpdates requestUpdates) {
              if (requestUpdates.hasUpdatedRequests()) {
                Set<String> updatedRequestIds = requestUpdates.getRequestUpdates().keySet();

                for (String id: updatedRequestIds) {
                  Log.e(TAG, String.format("Request %s has %d updates",
                      id, requestUpdates.getRequestUpdates().get(id)));
                }
              }
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
              // handle error
            }
          });
        }
      }
    }).start();
  }
}
