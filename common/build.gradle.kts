plugins {

}

repositories {

}

dependencies {
    compileOnlyApi(libs.bundles.common.provided)
    compileOnlyApi(libs.bundles.common.loaded)
    api(libs.bundles.common.included)
    implementation(libs.bundles.common.relocated)
}