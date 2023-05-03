/**
 * Created by Sergey Chuprin on 07.06.2019.
 */

const val PUBLISHING_GROUP = "io.blockify"

object AppConfig {

    object AppCoordinates {
        const val APP_ID = "io.blockify"

        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0-alpha01"
    }

    object LibraryAndroidCoordinates {
        const val LIBRARY_VERSION = "1.0.0"
        const val LIBRARY_VERSION_CODE = 1
    }
    const val MIN_SDK = 21
    const val TARGET_SDK = 29
    const val COMPILE_SDK = 33

    object BuildTypes {
        const val DEV = "dev"
        const val DEBUG = "debug"
        const val RELEASE = "release"
        const val STAGING = "staging"
    }

    object BuildModes {
        const val FULL = "full"
        const val DEMO = "demo"
        const val MIN_API24 = "minApi24"
        const val MIN_API23 = "minApi23"
        const val MIN_API21 = "minApi21"
    }

}


