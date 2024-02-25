plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.lx.runwithus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lx.runwithus"
        minSdk = 31
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 레트로핏 라이브러리 추가
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // 글라이드
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("jp.wasabeef:glide-transformations:4.3.0")

    // 위험 권한
    implementation("com.guolindev.permissionx:permissionx:1.7.1")

    // 내 위치 확인
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // 지도
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // 프래그먼트
    implementation ("androidx.fragment:fragment:1.3.0")

    // 파이어베이스
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.9.0")
    implementation("com.google.firebase:firebase-storage")

    //카운트다운 애니메이션
    implementation("com.airbnb.android:lottie:6.1.0")

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("me.relex:circleindicator:2.1.6")

    //recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")


}