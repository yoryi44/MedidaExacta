package com.jorgemeza.medidaexacta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.jorgemeza.medidaexacta.navigation.Menu
import com.jorgemeza.medidaexacta.navigation.NavigationHost
import com.jorgemeza.medidaexacta.ui.theme.MedidaExactaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.isLoading
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedidaExactaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding))
                    {
                        val navController = rememberNavController()
                        NavigationHost(
                            navHostController = navController,
                            startDestination = getDestination(),
                        )
                    }
                }
            }
        }
    }

    private fun getDestination(): Any {

//        if(mainViewModel.isLoggedIn)
//        {
//            return Home
//        }
//
//        if(mainViewModel.hasSeenOnboarding)
//        {
//            return Login
//        }
//
//        return Onboarding

        return Menu
    }

}
