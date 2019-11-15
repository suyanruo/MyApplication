package com.example.myapplication.zendesk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;

import java.util.List;

import zendesk.support.Article;
import zendesk.support.Category;
import zendesk.support.HelpCenterProvider;
import zendesk.support.Section;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;
import zendesk.support.guide.ViewArticleActivity;
import zendesk.support.request.RequestActivity;
import zendesk.support.request.RequestUiConfig;
import zendesk.support.requestlist.RequestListActivity;
import zendesk.support.requestlist.RequestListUiConfig;

public class ZendeskActivity extends AppCompatActivity {
  private static final String TAG = "ZendeskActivity";
  private EditText nameEdit;
  private EditText emailEdit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e(TAG, "onCreate: ");
    setContentView(R.layout.activity_zendesk);

    nameEdit = findViewById(R.id.et_name);
    emailEdit = findViewById(R.id.et_email);

    // 客服
    findViewById(R.id.btn_help_center).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        HelpCenterActivity.builder()
            .show(ZendeskActivity.this);
      }
    });
    findViewById(R.id.btn_view_article).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fetchZendeskInfo();
//        ZendeskActivity.builder().show(MainActivity.this);
      }
    });
    findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        CustomField field = new CustomField(360020242434L, "customfield");
        RequestActivity
            .builder()
//            .withRequestSubject("Dota2")
////            .withTags("game")
//            .withFiles(new File(FILE_PATH))
//            .withCustomFields(Arrays.asList(field))
            .show(ZendeskActivity.this);

      }
    });
    findViewById(R.id.btn_request_list).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        RequestListActivity
            .builder()
            .show(ZendeskActivity.this);
      }
    });
    findViewById(R.id.btn_start_update_zendesk).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startZendeskService();
      }
    });

    findViewById(R.id.btn_change_identity).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        MyApplication.getApplication().setIdentity("zj", "dimghost1@gmail.com");

        MyApplication.getApplication().setIdentity(nameEdit.getText().toString(), emailEdit.getText().toString());
        Log.e(TAG, "id: " + nameEdit.getText().toString() + "   " + emailEdit.getText().toString());
      }
    });
    findViewById(R.id.btn_back_to_main).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ZendeskActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
      }
    });
  }

  private void startZendeskService() {
    Intent intent = new Intent(ZendeskActivity.this, UpdateZendeskService.class);
    startService(intent);
  }

  private void fetchZendeskInfo() {
    final HelpCenterProvider provider = Support.INSTANCE.provider().helpCenterProvider();
    provider.getCategories(new ZendeskCallback<List<Category>>() {
      @Override
      public void onSuccess(List<Category> categories) {
        if (categories == null || categories.size() == 0) {
          return;
        }
        provider.getSections(categories.get(0).getId(), new ZendeskCallback<List<Section>>() {
          @Override
          public void onSuccess(List<Section> sections) {
            if (sections == null || sections.size() == 0) {
              return;
            }
            provider.getArticles(sections.get(0).getId(), new ZendeskCallback<List<Article>>() {
              @Override
              public void onSuccess(List<Article> articles) {
                if (articles == null || articles.size() == 0) {
                  return;
                }
                ViewArticleActivity.builder(articles.get(0).getId()).show(ZendeskActivity.this);
              }

              @Override
              public void onError(ErrorResponse errorResponse) {

              }
            });
          }

          @Override
          public void onError(ErrorResponse errorResponse) {

          }
        });
      }

      @Override
      public void onError(ErrorResponse errorResponse) {

      }
    });
  }

}
