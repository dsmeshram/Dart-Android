# Dart-Android

Very first let's solve the WH-Type question's

Why is Dart?

Every day in developer life they need to add/update/delete something in there app. the main pain is they need to update there app and publish again and again. its take too much time sometime(some time google may update the policy :( and some time developer missed something). let's try to solved this.

What it is?

its open source android(othere platform will be available very soon) lib. 

How its work?

Dart is open source lib, dart through you can update any kind of value without re-publishing the application. just you need to maintain
json file on your server and whenever and whatever need ro update you just need to update the values in your remote json file and your 
app will sync automaticly and update new information

How to use?

Create the Android application using API level 21 and above
add dartlib lib in your project

implementation project(":dartlib")

Create Application class in your Applicaion
in Application class initlized dartlib, pass the application context and your server response json file

@Override
public void onCreate() {
    super.onCreate();
    Dartlib.getInstance().init(this,"http://<hostname>/rest/response.json");
}

now your lib ready to live

get updated value form server and set to the UI component

 ((TextView)findViewById(R.id.appname)).setText(Dartlib.getInstance().getValueWithAlt("app_name","Appname"));
 
response.json structure(key value paire)

{
	"vr":10,
	"app_name":"App Name",
	"app_name_text_color": "#FFFFFF",
	"app_message":"versition 0.8",
	"app_message_text_color": "#FFFFFF",
	"bg_color":"#f1f1f1"
}




    
    
    
    
    



