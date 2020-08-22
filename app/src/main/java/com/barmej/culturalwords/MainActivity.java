package com.barmej.culturalwords;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;
    ImageView imageViewQuestion;
    String randomImagesNames;
    ImageButton imageAnswerPage;
    ImageView languageChange;
    int randomImageQuestion;

    int[] imagesQuestion = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13
    };

    String[] imagesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("app pref", MODE_PRIVATE);
        String appLang = sharedPreferences.getString("Keep Language", "");
        if (!appLang.equals("")) LocaleHelper.setLocale(this, appLang);
        imagesNames = getResources().getStringArray(R.array.answers);
        imageViewQuestion = findViewById(R.id.image_view_question);
        imageAnswerPage = findViewById(R.id.button_open_answer);
        languageChange = findViewById(R.id.button_change_language);

        languageChange.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            button();

        }
        });

    }

    public boolean button() {
        showLanguageDialog();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.langueageMenu) {
            showLanguageDialog();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.change_lang_text).setItems(R.array.Languages, new DialogInterface.OnClickListener() {@Override
        public void onClick(DialogInterface dialogInterface, int which) {
            String language = "ar";
            switch (which) {
                case 0:
                    language = "ar";
                    break;
                case 1:
                    language = "en";
                    break;
            }
            saveLanguage(language);
            LocaleHelper.setLocale(MainActivity.this, language);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        }).create();
        alertDialog.show();
    }

    public void saveLanguage(String lang) {
        SharedPreferences sharedPreferences = getSharedPreferences("Keep Language", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app lang", lang);
        editor.apply();
    }

    /**
     * يجب عليك كتابة الكود الذي يقوم بمشاركة الصورة في هذه الدالة
     */
    private void shareImage() {
        // كود مشاركة الصورة هنا
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        //        intent.putExtra(Intent.EXTRA_EMAIL , );

    }
    /**
     *  هذه الدالة تقوم بطلب صلاحية الكتابة على ال external storage حتى يمكن حفظ ملف الصورة
     * <p>
     * وفي حال الحصول على الصلاحية تقوم باستدعاء دالة shareImage لمشاركة الصورة
     **/
    private void checkPermissionAndShare() {
        // insertImage في النسخ من آندرويد 6 إلى آندرويد 9 يجب طلب الصلاحية لكي نتمكن من استخدام الدالة
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // هنا لا يوجد صلاحية للتطبيق ويجب علينا طلب الصلاحية منه عن طريك الكود التالي
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // بسبب عدم منح المستخدم الصلاحية للتطبيق فمن الأفضل شرح فائدتها له عن طريق عرض رسالة تشرح ذلك
                // هنا نقوم بإنشاء AlertDialog لعرض رسالة تشرح للمستخدم فائدة منح الصلاحية
                AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.permission_title).setMessage(R.string.permission_explanation).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {@Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // requestPermissions عند الضغط على زر منح نقوم بطلب الصلاحية عن طريق الدالة
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {@Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //  عند الضغط على زر منع نقوم بإخفاء الرسالة وكأن شيء لم يكن
                    dialogInterface.dismiss();
                }
                }).create();

                // نقوم بإظهار الرسالة بعد إنشاء alertDialog
                alertDialog.show();

            } else {
                // لا داعي لشرح فائدة الصلاحية للمستخدم ويمكننا طلب الصلاحية منه
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }

        } else {
            // الصلاحية ممنوحه مسبقا لذلك يمكننا مشاركة الصورة
            shareImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // يتم استدعاء هذه الدالة بعد اختيار المستخدم احد الخيارين من رسالة طلب الصلاحية
        if (requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // تم منح الصلاحية من قبل المستخدم لذلك يمكننا مشاركة الصورة الآن
                shareImage();
            } else {
                // لم يتم منح الصلاحية من المستخدم لذلك لن نقوم بمشاركة الصورة، طبعا يمكننا تنبيه المستخدم بأنه لن يتم مشاركة الصورة لسبب عدم منح الصلاحية للتطبيق
                Toast.makeText(MainActivity.this, R.string.permission_explanation, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void randomImageQuestion(View view) {
        Random random = new Random();
        int randomImage = random.nextInt(imagesQuestion.length);
        randomImageQuestion = imagesQuestion[randomImage];
        randomImagesNames = imagesNames[randomImage];
        imageViewQuestion.setImageResource(randomImageQuestion);

    }

    public void answerPage(View view) {
        Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
        intent.putExtra("AnswerPackage", randomImagesNames);
        startActivity(intent);
    }

    public void sharePage(View view) {
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra("QuestionShare", randomImageQuestion);
        startActivity(intent);
    }

    public void changeLanguage(View view) {
        imagesNames = getResources().getStringArray(R.array.answers);
    }

}