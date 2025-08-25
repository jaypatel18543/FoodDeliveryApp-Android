package com.example.fooddeliveryapk;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddeliveryapk.chefFoodPanel.Chef;
import com.example.fooddeliveryapk.chefFoodPanel.FoodDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.material.textfield.TextInputLayout;
import com.mayank.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class chef_postDish extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton image_upload;
    private Button post;
    private Spinner dishes;
    private TextInputLayout description, quantity, price;
    String descrption, Quantity, pricee, dishess;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, dataa;
    private StorageReference ref;
    private FirebaseAuth fAuth;
    private String ChefID, State, City, Area, RandomUID;
    private CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_post_dish);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dishes = findViewById(R.id.dishes);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        quantity = findViewById(R.id.Quantity);
        price = findViewById(R.id.price);
        image_upload = findViewById(R.id.image_upload);
        cropImageView = findViewById(R.id.cropImageView);  // Ensure this ID matches your XML layout
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FoodDetails");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = firebaseDatabase.getInstance().getReference("Chef").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Chef cheff = snapshot.getValue(Chef.class);
                    State = cheff.getState();
                    City = cheff.getCity();
                    Area = cheff.getArea();
                    image_upload = findViewById(R.id.image_upload);

                    image_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectImageClick(v);
                        }
                    });

                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dishess = dishes.getSelectedItem().toString().trim();
                            descrption = description.getEditText().getText().toString().trim();
                            Quantity = quantity.getEditText().getText().toString().trim();
                            pricee = price.getEditText().getText().toString().trim();
                            if (isValid(descrption, Quantity, pricee)) {
                                uploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Error: ", error.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(chef_postDish.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ChefID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FoodDetails info = new FoodDetails(dishess, Quantity, pricee, descrption, String.valueOf(uri), RandomUID, ChefID);
                            FirebaseDatabase.getInstance().getReference("FoodDetails").child(State).child(City).child(Area).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(chef_postDish.this, "Dish Posted Successfully!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(chef_postDish.this, "Failed to post dish: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(chef_postDish.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    private boolean isValid(String descrip, String quantity, String price) {
        boolean isValid = true;

        if (descrip.isEmpty()) {
            description.setError("Description is Required");
            isValid = false;
        } else {
            description.setError(null);
        }

        if (quantity.isEmpty()) {
            this.quantity.setError("Enter Number of Plates or Items");
            isValid = false;
        } else {
            this.quantity.setError(null);
        }

        if (price.isEmpty()) {
            this.price.setError("Please Mention Price");
            isValid = false;
        } else {
            this.price.setError(null);
        }

        return isValid;
    }

    private void onSelectImageClick(View v) {
        // Start an intent to select an image from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                // Load the selected image into the crop view
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                cropImageView.setImageBitmap(bitmap); // Set image to CropImageView
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to crop the image when a crop button is clicked (if needed)
    private void cropImage() {
        Bitmap croppedImage = cropImageView.getCroppedImage(); // Cropping the image
        if (croppedImage != null) {
            image_upload.setImageBitmap(croppedImage);
            imageUri = getImageUri(this, croppedImage); // Converting cropped Bitmap to Uri
        }
    }

    // Convert cropped Bitmap to Uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
