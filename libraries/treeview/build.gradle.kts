plugins {
    id("com.android.library")
}

android.namespace = ModulesConfig.TreeViewNamespace
android.defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
android.defaultConfig.consumerProguardFiles("consumer-rules.pro")

dependencies {
    // android dependencies
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // test dependencies
    implementation(libs.junit)
    implementation(libs.extJunit)
    implementation(libs.espresso.core)
}