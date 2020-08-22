package com.barmej.culturalwords;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import static android.content.Intent.createChooser;

public class ShareActivity extends AppCompatActivity {

    ImageView sharedQuestion;
    EditText ShareTitle;
    Button shareButton;
    Drawable mDrawableImage;
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ShareTitle = findViewById(R.id.edit_text_share_title);
        shareButton = findViewById(R.id.button_share_question);
        sharedQuestion = findViewById(R.id.image_view_question);
        sharedQuestion.setImageResource(getIntent().getIntExtra("QuestionShare", 14));

        SharedPreferences sharedPreferences = getSharedPreferences("lastEditText" , MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString("Share Title" , "");
        ShareTitle.setText(questionTitle);
    }

    private void shareImage() {

    }

    private void checkPermissionAndShare() {
        // insertImage في النسخ من آندرويد 6 إلى آندرويد 9 يجب طلب الصلاحية لكي نتمكن من استخدام الدالة
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // هنا لا يوجد صلاحية للتطبيق ويجب علينا طلب الصلاحية منه عن طريك الكود التالي
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // بسبب عدم منح المستخدم الصلاحية للتطبيق فمن الأفضل شرح فائدتها له عن طريق عرض رسالة تشرح ذلك
                // هنا نقوم بإنشاء AlertDialog لعرض رسالة تشرح للمستخدم فائدة منح الصلاحية
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.permission_title)
                        .setMessage(R.string.permission_explanation)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // requestPermissions عند الضغط على زر منح نقوم بطلب الصلاحية عن طريق الدالة
                                ActivityCompat.requestPermissions(ShareActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //  عند الضغط على زر منع نقوم بإخفاء الرسالة وكأن شيء لم يكن
                                dialogInterface.dismiss();
                            }
                        }).create();

                // نقوم بإظهار الرسالة بعد إنشاء alertDialog
                alertDialog.show();

            } else {
                // لا داعي لشرح فائدة الصلاحية للمستخدم ويمكننا طلب الصلاحية منه
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }

        } else {
            // الصلاحية ممنوحه مسبقا لذلك يمكننا مشاركة الصورة

        }
    }

    public void shareButton(View view) {
        Uri imageUri = Uri.parse("android.resource://slidenerd.vivz/drawable/" +R.drawable.icon_13);
                // كود ال BitMap - لا يعمل
                /** mDrawableImage = sharedQuestion.getDrawable();
                 Bitmap bitmap = ((BitmapDrawable)mDrawableImage).getBitmap();
                 try {
                 File file = new File(ShareActivity.this.getExternalCacheDir(), "imageShare");
                 FileOutputStream fOut = new FileOutputStream(file);
                 bitmap.compress(Bitmap.CompressFormat.PNG , 80 , fOut);
                 fOut.flush();
                 fOut.close();
                 file.setReadable(true , false);
                 Intent intent2 = new Intent(Intent.ACTION_SEND);
                 intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent2.putExtra(Intent.EXTRA_STREAM , Uri.fromFile(file));
                 intent2.setType("image/png");
                 startActivity(Intent.createChooser(intent2 , "share Image Via"));

                 } catch (FileNotFoundException e) {
                 e.printStackTrace();
                 Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
                 } catch (Exception e) {
                 e.printStackTrace();
                 } **/

                final ImageView questionImage = sharedQuestion;
                String questionTitle = ShareTitle.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_TEXT , questionTitle + "\n");
                intent.putExtra(Intent.EXTRA_STREAM , imageUri + "\n");
                intent = Intent.createChooser(intent , "Send Image");
                startActivity(intent);


//                Intent shareIntent = Intent.createChooser(intent, null);
//        startActivity(shareIntent);
//        SharedPreferences sharedPreferences = getSharedPreferences("lastEditText" , MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("Share Title" , questionTitle);
//        editor.apply();

                // كود Intent للصور ولكن لم يعمل أيضا :(
//                Intent intent1 = new Intent();
//
//                startActivity(intent1);
            }
}

