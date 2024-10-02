plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "jt.projects.employeestest"
    compileSdk = 34

    defaultConfig {
        applicationId = "jt.projects.employeestest"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions += "client"
    productFlavors {
        create("operatorA") {
            dimension = "client"
            applicationIdSuffix += ".operatorA"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorA\"")
            buildConfigField("String", "CLIENT_COLOR", "\"FF5733\"")
        }
        create("operatorB") {
            dimension = "client"
            applicationIdSuffix += ".operatorB"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorB\"")
            buildConfigField("String", "CLIENT_COLOR", "\"33FF57\"")
        }
        create("operatorC") {
            dimension = "client"
            applicationIdSuffix += ".operatorC"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorC\"")
            buildConfigField("String", "CLIENT_COLOR", "\"3357FF\"")
        }
        create("operatorD") {
            dimension = "client"
            applicationIdSuffix += ".operatorD"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorD\"")
            buildConfigField("String", "CLIENT_COLOR", "\"FF33A1\"")
        }
        create("operatorE") {
            dimension = "client"
            applicationIdSuffix += ".operatorE"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorE\"")
            buildConfigField("String", "CLIENT_COLOR", "\"A133FF\"")
        }
        create("operatorF") {
            dimension = "client"
            applicationIdSuffix += ".operatorF"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorF\"")
            buildConfigField("String", "CLIENT_COLOR", "\"FF8C33\"")
        }
        create("operatorG") {
            dimension = "client"
            applicationIdSuffix += ".operatorG"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorG\"")
            buildConfigField("String", "CLIENT_COLOR", "\"33FF8C\"")
        }
        create("operatorH") {
            dimension = "client"
            applicationIdSuffix += ".operatorH"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorH\"")
            buildConfigField("String", "CLIENT_COLOR", "\"8C33FF\"")
        }
        create("operatorI") {
            dimension = "client"
            applicationIdSuffix += ".operatorI"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorI\"")
            buildConfigField("String", "CLIENT_COLOR", "\"FF3333\"")
        }
        create("operatorJ") {
            dimension = "client"
            applicationIdSuffix += ".operatorJ"
            buildConfigField("String", "CLIENT_NAME", "\"OperatorJ\"")
            buildConfigField("String", "CLIENT_COLOR", "\"33FFA1\"")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.base)
    implementation(libs.play.services.basement)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Зависимости для HMS
    implementation (libs.base)
    implementation ("com.huawei.hms:location:5.0.0.300")
}