plugins {
    id("com.android.library")
}

android.namespace = ModulesConfig.TreeViewNamespace
android.defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
android.defaultConfig.consumerProguardFiles("consumer-rules.pro")

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.junit)
    implementation(libs.extJunit)
    implementation(libs.espresso.core)
}