package com.smartmechanic.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.smartmechanic.data.User
import com.smartmechanic.data.Mechanic

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db   = FirebaseFirestore.getInstance()

    fun registerUser(name: String, email: String, password: String,
                     onSuccess: ()->Unit, onFailure:(String)->Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = auth.currentUser!!.uid
                val user = User(uid, name, email)
                db.collection("users").document(uid).set(user)
                  .addOnSuccessListener{ onSuccess() }
                  .addOnFailureListener{ e-> onFailure(e.message ?: "") }
            } else onFailure(it.exception?.message ?: "")
        }
    }

    fun registerMechanic(name:String, email:String, password:String,
                         license:String, onSuccess:()->Unit, onFailure:(String)->Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = auth.currentUser!!.uid
                val mech = Mechanic(uid, name, 0.0, 0.0, license = license)
                db.collection("mechanics").document(uid).set(mech)
                  .addOnSuccessListener{ onSuccess() }
                  .addOnFailureListener{ e-> onFailure(e.message ?: "") }
            } else onFailure(it.exception?.message ?: "")
        }
    }

    fun login(email:String, password:String,
              onSuccess:(role:String)->Unit, onFailure:(String)->Unit) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = auth.currentUser!!.uid
                // check role in Firestore
                db.collection("users").document(uid).get().addOnSuccessListener { doc ->
                    if (doc.exists()) onSuccess("user") 
                    else {
                        db.collection("mechanics").document(uid).get().addOnSuccessListener { mdoc->
                            if (mdoc.exists()) onSuccess("mechanic")
                            else onSuccess("admin") // fallback: treat as admin
                        }
                    }
                }
            } else onFailure(it.exception?.message ?: "")
        }
    }
}
