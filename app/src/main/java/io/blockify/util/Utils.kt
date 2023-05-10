//package ir.blockify.utils
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.*
//import androidx.navigation.compose.ComposeNavigator
//
//fun NavGraphBuilder.composable(
//    route: String,
//    arguments: List<NamedNavArgument> = emptyList(),
//    deepLinks: List<NavDeepLink> = emptyList(),
//    content: @Composable (NavBackStackEntry) -> Unit
//) {
//    addDestination(
//        ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
//            this.route = route
//            arguments.forEach { (argumentName, argument) ->
//                addArgument(argumentName, argument)
//            }
//            deepLinks.forEach { deepLink ->
//                addDeepLink(deepLink)
//            }
//        }
//    )
//}
//
//fun validateEmail(email: String): Boolean {
//    return email.length > 8
//}
//
//fun validatePassword(password: String): Boolean {
//    return true
//}
