//package com.sibertama.bigforum.Network;
//
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
///**
// * Created by Semmy
// * mr.shanky08@gmail.com on 8/16/16.
// *
// * @copyright 2016
// * PT.Bisnis Indonesia Sibertama
// */
//public class FirebaseCloudMessaging extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyFirebaseIIDService";
//
//    @Override
//    public void onTokenRefresh() {
//
//        //Getting registration token
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//
//        //Displaying token on logcat
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//    }
//
//    private void sendRegistrationToServer(String token) {
//        //You can implement this method to store the token on your server
//        //Not required for current project
//    }
//}
