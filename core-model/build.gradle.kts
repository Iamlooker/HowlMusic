plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

apply<ModuleDefaultPlugin>()

android {
	compileSdk = Android.compileSdk
	namespace = "com.looker.core_model"

	kotlinOptions {
		jvmTarget = "11"
	}
}