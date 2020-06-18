package com.col740.myhospital

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var emailText: EditText? = null
    private var passwordText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        emailText = findViewById<View>(R.id.editEmail) as EditText
        passwordText = findViewById<View>(R.id.editPass) as EditText


        val buttonLogin = findViewById<View>(R.id.buttonLoginIn) as Button

        logintoolbar.setTitle("Login")
        buttonLogin.setOnClickListener({ startSignIn() })
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//        mAuthListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser()!=null){
//                    String idtemp=firebaseAuth.getCurrentUser().getEmail();
//                    final String id=idtemp.split("\\.")[0];
//                    System.out.println("ID "+id);
//                    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
//                    final Intent intent=new Intent(LoginPage.this, Drawer.class);
//                    DatabaseReference doctor=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/"+id);
//                    doctor.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot d) {
//                            if(d.hasChildren()) {
//                                String[] info = {"Name", "E-mail", "Specialisation", "Hospital", "Rating", "Base Charge", "Booking"};
//                                for (int i = 0; i < info.length; i++) {
//                                    info[i] = d.child(info[i]).getValue().toString();
//                                }
//                                intent.putExtra("isDoctor", true);
//                                intent.putExtra("info", info);
//                                startActivity(intent);
//                            }
//                            else{
//                                final DatabaseReference patient=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/"+id);
//                                patient.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot data) {
//                                        System.out.println("KYUN?? "+data.toString());
//                                        String[] info={"Name","E-mail","Problem","Phone","Appointment"};
//                                        for(int i=0;i<info.length;i++){
//                                            info[i]=data.child(info[i]).getValue().toString();
//                                        }
//                                        intent.putExtra("isDoctor",false);
//                                        intent.putExtra("info",info);
//                                        startActivity(intent);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//        };
//    }
    private fun startSignIn() {
        val email = emailText!!.text.toString()
        val password = passwordText!!.text.toString()
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this@LoginActivity, "Fields Empty", Toast.LENGTH_LONG).show()
        } else {
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Sign In Problem", Toast.LENGTH_LONG).show()
                } else {
                    val id = email.split(".").toTypedArray()[0]
                    println("ID $id")
                    val database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    val doctor =
//                        database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/$id")
//                    doctor.addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(d: DataSnapshot) {
//                            if (d.hasChildren()) {
//                                val info = arrayOf(
//                                    "Name",
//                                    "E-mail",
//                                    "Specialisation",
//                                    "Hospital",
//                                    "Rating",
//                                    "Base Charge",
//                                    "Booking",
//                                    "Booked By"
//                                )
//                                for (i in info.indices) {
//                                    info[i] = d.child(info[i]).value.toString()
//                                }
//                                intent.putExtra("isDoctor", true)
//                                intent.putExtra("info", info)
//                                startActivity(intent)
//                            } else {
                    val patient =
                        database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/$id")
                    patient.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(data: DataSnapshot) {
                            val info =
                                arrayOf(
                                    "Name",
                                    "E-mail",
                                    "Problem",
                                    "Phone",
                                    "Appointment"
                                )
                            for (i in info.indices) {
                                info[i] =
                                    data.child(info[i]).value.toString()
                            }
                            intent.putExtra("id", id)
                            startActivity(intent)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }
            }

           //             override fun onCancelled(databaseError: DatabaseError) {}
        }
    }
}
//        }
//    }
//}
