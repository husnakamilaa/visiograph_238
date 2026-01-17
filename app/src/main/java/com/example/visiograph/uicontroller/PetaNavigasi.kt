package com.example.visiograph.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.visiograph.uicontroller.route.DestinasiHome
import com.example.visiograph.uicontroller.route.DestinasiLogin
import com.example.visiograph.uicontroller.route.DestinasiSplash
import com.example.visiograph.uicontroller.route.anggota.DestinasiAnggotaDetail
import com.example.visiograph.uicontroller.route.anggota.DestinasiAnggotaEdit
import com.example.visiograph.uicontroller.route.anggota.DestinasiAnggotaEntry
import com.example.visiograph.uicontroller.route.anggota.DestinasiAnggotaList
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangDetail
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangEdit
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangEntry
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangList
import com.example.visiograph.uicontroller.route.kerusakan.DestinasiKerusakanDetail
import com.example.visiograph.uicontroller.route.kerusakan.DestinasiKerusakanEdit
import com.example.visiograph.uicontroller.route.kerusakan.DestinasiKerusakanEntry
import com.example.visiograph.uicontroller.route.kerusakan.DestinasiKerusakanList
import com.example.visiograph.uicontroller.route.peminjaman.DestinasiPeminjamanDetail
import com.example.visiograph.uicontroller.route.peminjaman.DestinasiPeminjamanEdit
import com.example.visiograph.uicontroller.route.peminjaman.DestinasiPeminjamanEntry
import com.example.visiograph.uicontroller.route.peminjaman.DestinasiPeminjamanList
import com.example.visiograph.view.HomeScreen
import com.example.visiograph.view.LoginScreen
import com.example.visiograph.view.SplashScreen
import com.example.visiograph.view.anggota.DetailAnggotaScreen
import com.example.visiograph.view.anggota.EditAnggotaScreen
import com.example.visiograph.view.anggota.EntryAnggotaScreen
import com.example.visiograph.view.anggota.ListAnggotaScreen
import com.example.visiograph.view.barang.DetailBarangScreen
import com.example.visiograph.view.barang.EditBarangScreen
import com.example.visiograph.view.barang.EntryBarangScreen
import com.example.visiograph.view.barang.ListBarangScreen
import com.example.visiograph.view.kerusakan.DetailKerusakanScreen
import com.example.visiograph.view.kerusakan.EditKerusakanScreen
import com.example.visiograph.view.kerusakan.EntryKerusakanScreen
import com.example.visiograph.view.kerusakan.ListKerusakanScreen
import com.example.visiograph.view.peminjaman.DetailPeminjamanScreen
import com.example.visiograph.view.peminjaman.EditPeminjamanScreen
import com.example.visiograph.view.peminjaman.EntryPeminjamanScreen
import com.example.visiograph.view.peminjaman.ListPeminjamanScreen

@Composable
fun VisiographApp(navController: NavHostController = rememberNavController(),
                 modifier: Modifier){
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = Modifier
    ) {

        composable(DestinasiSplash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiSplash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(DestinasiLogin.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToMenu = { route ->
                    navController.navigate(route)
                },
                onLogoutSuccess = {
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }

        // ANGGOTA KIDS //

        composable(DestinasiAnggotaList.route) {
            ListAnggotaScreen(
                navigateToItemEntry = { navController.navigate(DestinasiAnggotaEntry.route) },
                navigateToDetail = { idAnggota ->
                    navController.navigate("${DestinasiAnggotaDetail.route}/$idAnggota")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiAnggotaEntry.route) {
            EntryAnggotaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiAnggotaDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiAnggotaDetail.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailAnggotaScreen(
                navigateToEdit = { navController.navigate("${DestinasiAnggotaEdit.route}/$it") },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiAnggotaEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiAnggotaEdit.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditAnggotaScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = {navController.navigateUp()}
            )
        }

        // BARANG KIDSS ///

        composable(DestinasiBarangList.route) {
            ListBarangScreen(
                navigateToItemEntry = { navController.navigate(DestinasiBarangEntry.route) },
                navigateToDetail = { idBarang ->
                    navController.navigate("${DestinasiBarangDetail.route}/$idBarang")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiBarangEntry.route) {
            EntryBarangScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiBarangDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiBarangDetail.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailBarangScreen(
                navigateToEdit = {
                    navController.navigate("${DestinasiBarangEdit.route}/$it")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiBarangEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiBarangEdit.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditBarangScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

//        BUATTT PEMINJAMAN YAA KIDSS
        composable(DestinasiPeminjamanList.route) {
            ListPeminjamanScreen(
                navigateToEntry = { navController.navigate(DestinasiPeminjamanEntry.route) },
                navigateToDetail = { idPinjam ->
                    navController.navigate("${DestinasiPeminjamanDetail.route}/$idPinjam")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiPeminjamanEntry.route) {
            EntryPeminjamanScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiPeminjamanDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiPeminjamanDetail.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailPeminjamanScreen(
                navigateToEdit = {
                    navController.navigate("${DestinasiPeminjamanEdit.route}/$it")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiPeminjamanEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiPeminjamanEdit.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditPeminjamanScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // NIII BUAT YG KERUSKAANAN YOWW
        composable(DestinasiKerusakanList.route) {
            ListKerusakanScreen(
                navigateToItemEntry = { navController.navigate(DestinasiKerusakanEntry.route) },
                navigateToDetail = { idKerusakan ->
                    navController.navigate("${DestinasiKerusakanDetail.route}/$idKerusakan")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiKerusakanEntry.route) {
            EntryKerusakanScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiKerusakanDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiKerusakanDetail.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailKerusakanScreen(
                navigateToEdit = {
                    navController.navigate("${DestinasiKerusakanEdit.route}/$it")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiKerusakanEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiKerusakanEdit.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditKerusakanScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}