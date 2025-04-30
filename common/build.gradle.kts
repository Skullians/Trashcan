plugins {

}

repositories {

}

dependencies {
    compileOnly(libs.bundles.common.provided)
    compileOnlyApi(libs.bundles.common.loaded)
    api(libs.bundles.common.included)
}