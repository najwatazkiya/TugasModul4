package com.example.tugasmodul4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HalamanTiket()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanTiket() {

    var hargaTiket by rememberSaveable {
        mutableIntStateOf(50000)
    }

    var jumlahTiket by rememberSaveable {
        mutableIntStateOf(1)
    }

    var namaPembeli by rememberSaveable {
        mutableStateOf("")
    }

    var status by rememberSaveable {
        mutableStateOf("Nama Masih Kosong")
    }

    var sedangMemproses by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(sedangMemproses) {

        if (sedangMemproses) {

            delay(5000)

            status = "Tiket telah dipesan"

            sedangMemproses = false
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Pemesanan Tiket"
                    )
                }
            )
        }

    ) { paddingValues ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),

            verticalArrangement = Arrangement.Top
        ) {

            PemesananTiket(

                namaPembeli = namaPembeli,
                jumlahTiket = jumlahTiket,
                hargaTiket = hargaTiket,

                onNamaChange = { namaBaru ->

                    namaPembeli = namaBaru

                    // Jika nama kosong
                    if (namaBaru.isBlank()) {

                        status = "Nama Masih Kosong"

                    } else {

                        status = "Silakan pesan tiket"
                    }
                },

                onTambahTiket = {

                    jumlahTiket++
                },

                onKurangTiket = {

                    if (jumlahTiket > 1) {

                        jumlahTiket--
                    }
                },

                onPesanTiket = {


                    if (namaPembeli.isBlank()) {

                        status = "Nama Masih Kosong"

                    } else {

                        status = "Memproses pesanan..."

                        sedangMemproses = true
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            StatusPesanan(
                status = status
            )
        }
    }
}

@Composable
fun PemesananTiket(

    namaPembeli: String,

    jumlahTiket: Int,

    hargaTiket: Int,

    onNamaChange: (String) -> Unit,

    onTambahTiket: () -> Unit,

    onKurangTiket: () -> Unit,

    onPesanTiket: () -> Unit
) {


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Nama Pembeli",
            style = MaterialTheme.typography.bodyLarge
        )


        Spacer(
            modifier = Modifier.height(6.dp)
        )


        OutlinedTextField(

            value = namaPembeli,

            onValueChange = onNamaChange,

            placeholder = {
                Text(
                    text = "Masukkan nama Anda"
                )
            },

            modifier = Modifier.fillMaxWidth(),

            singleLine = true
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Harga Tiket",
            style = MaterialTheme.typography.bodyLarge
        )


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        Text(
            text = "Rp ${hargaTiket.formatRupiah()}",
            style = MaterialTheme.typography.titleMedium
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Jumlah Tiket",
            style = MaterialTheme.typography.bodyLarge
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Row(

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.Center,

            verticalAlignment = Alignment.CenterVertically
        ) {


            Button(
                onClick = onKurangTiket
            ) {

                Text(
                    text = "-"
                )
            }


            Spacer(
                modifier = Modifier.width(30.dp)
            )

            Text(

                text = jumlahTiket.toString(),

                style = MaterialTheme.typography.titleLarge
            )


            Spacer(
                modifier = Modifier.width(30.dp)
            )

            Button(
                onClick = onTambahTiket
            ) {

                Text(
                    text = "+"
                )
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(

            text = "Total: Rp ${(hargaTiket * jumlahTiket).formatRupiah()}",

            style = MaterialTheme.typography.titleMedium
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(

            onClick = onPesanTiket,

            modifier = Modifier.fillMaxWidth(),

            enabled = true
        ) {

            Text(
                text = "Pesan Tiket"
            )
        }
    }
}


@Composable
fun StatusPesanan(
    status: String
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors()
    ) {

        Text(

            text = "Status: $status",

            modifier = Modifier.padding(16.dp),

            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun Int.formatRupiah(): String {

    return this
        .toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}