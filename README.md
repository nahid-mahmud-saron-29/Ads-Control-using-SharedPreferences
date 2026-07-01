## ৩. তৃতীয় পদ্ধতি: `SharedPreferences` (প্রফেশনাল ও স্থায়ী পদ্ধতি)

আপনার তৃতীয় প্রজেক্ট বা ব্রাঞ্চের জন্য এই README ফাইলটি ব্যবহার করুন:

```markdown
# Android Ads Control using SharedPreferences

এই প্রজেক্টে লোকাল ডাটাবেজ বা মেমরি হিসেবে `SharedPreferences` ব্যবহার করা হয়েছে। এটি প্রফেশনাল অ্যাপ ডেভেলপমেন্টের জন্য একটি স্ট্যান্ডার্ড পদ্ধতি, যা অ্যাপের যেকোনো স্ক্রিন থেকে ডেটা অ্যাক্সেস করতে সাহায্য করে।

## 📌 এটি কীভাবে কাজ করে?
1. **MainActivity**-তে সার্ভার থেকে রেসপন্স আসার পর মানটি মোবাইলের লোকাল মেমরিতে (SharedPreferences) স্থায়ীভাবে সেভ করা হয়।
2. অ্যাপের ২য়, ৩য় বা ২০ নম্বর—যেকোনো স্ক্রিন থেকে সরাসরি এই লোকাল মেমরি চেক করা যায়।
3. অ্যাপ ব্যাকগ্রাউন্ড থেকে কেটে দিলেও এই ডেটা মুছে যায় না।

## 🛠️ কোড ইমপ্লিমেন্টেশন (Code Implementation)

### MainActivity.java (ডেটা সেভ করা)
```java
SharedPreferences sharedPreferences = getSharedPreferences("AdsSettings", MODE_PRIVATE);

// Volley Response এর ভেতরে:
if (response.contains("SHOW")){
    sharedPreferences.edit().putBoolean("SHOW_ADS", true).apply();
} else {
    sharedPreferences.edit().putBoolean("SHOW_ADS", false).apply();
}







MainActivity2.java 


SharedPreferences sharedPreferences = getSharedPreferences("AdsSettings", MODE_PRIVATE);
boolean shouldShowAds = sharedPreferences.getBoolean("SHOW_ADS", false);

if (shouldShowAds){
    adView.setVisibility(View.VISIBLE);
} else {
    adView.setVisibility(View.GONE);
}
