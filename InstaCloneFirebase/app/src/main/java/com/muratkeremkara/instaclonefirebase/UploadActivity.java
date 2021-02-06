package com.muratkeremkara.instaclonefirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    Bitmap selectedImage;
    ImageView imageView;
    EditText commentText;
    private FirebaseStorage firebaseStorage;//firebase depolama kullanmak için // depo
    private StorageReference storageReference;// verilerin nereye kaydedileceğini anlatıyor
    Uri imageData;// internetin ulaşabileceği yolu belirtiyor
    private FirebaseFirestore firebaseFirestore;// firestore veri tabanı //firestore veritabanı
    private FirebaseAuth firebaseAuth;// buda kullanıcın adını almak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.imageView);
        commentText = findViewById(R.id.commentText);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();//sırayla hepsinin özlellikleri bir nesneye aktarılıyor
        firebaseAuth = FirebaseAuth.getInstance();


    }

    public void upload(View view) {

        if (imageData != null) {


            //universal unique id
            UUID uuid = UUID.randomUUID();// universal uniqe id  bize uydurma bir id verecek random bir şekilde
            final String imageName = "images/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {// child komutu ile firestordda boş bir ilasor oluşturuluyor
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Download URL  uydurma id nin url sini alacaksin storogeye kaydedeceksin

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);// referencenı bul
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {// urlsini dowload et
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString();// oluşturulan url dowload ediliyor

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();// en son kullanıcı
                            String userEmail = firebaseUser.getEmail();

                            String comment = commentText.getText().toString();// atacağı yazı alınıyor

                            HashMap<String, Object> postData = new HashMap<>();// database oluştutrken hepsinin sırasını ve ne kaydedeceğini anlatır yani fotoğı koyarken fotonun tarih bilgisi felan
                            postData.put("useremail", userEmail);
                            postData.put("downloadurl", downloadUrl);
                            postData.put("comment", comment);
                            postData.put("date", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {// mesajı yukluyoruz
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Intent intent = new Intent(UploadActivity.this, FeedActivity.class);// akış sayfasına gönder
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// bütün aktiviteleri kapat
                                    startActivity(intent);// aktiviteyi göster

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }


    }

    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {// eğer izin yoksa
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {// izin zaten verilmişse
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Action pıck bir şey alıp çıkacağıını belirtiyor
            startActivityForResult(intentToGallery, 2);// bir sonuç için bu aktiviteyi başlat
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {// istenilen izin sonucunda ne yapılacağı


        if (requestCode == 1) {// izin kodu doğruysa
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {// yukarıdaki integer değerinin bos olup olmadığı kontrol edliyor
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// izni verildiği gibi gönderiyor
                startActivityForResult(intentToGallery, 2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {// istenilen aktivite sonucunda ne yapılacağı anlatılıyor

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageData = data.getData();

            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);// image i bilgisayarın istediği dile çevirdik decoder ile yani bitmap e
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    imageView.setImageBitmap(selectedImage);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}