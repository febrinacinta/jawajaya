package com.jawajaya.Database

class Api {
    companion object {
        private const val SERVER = "http://192.168.100.12/APi/"

        // URLs untuk tabel terjemahan
        const val CREATE_TERJEMAHAN = "${SERVER}create.php"
        const val READ_TERJEMAHAN = "${SERVER}read.php"
        const val DELETE_TERJEMAHAN = "${SERVER}delete.php"
        const val UPDATE_TERJEMAHAN = "${SERVER}update.php"

        // URLs untuk tabel kamus
        const val CREATE_KAMUS = "${SERVER}create_kamus.php"
        const val READ_KAMUS = "${SERVER}read_kamus.php"
        const val DELETE_KAMUS = "${SERVER}delete_kamus.php"
        const val UPDATE_KAMUS = "${SERVER}update_kamus.php"

        // URLs untuk tabel cerita
        const val CREATE_CERITA = "${SERVER}create_cerita.php"
        const val READ_CERITA = "${SERVER}read_cerita.php"
        const val DELETE_CERITA = "${SERVER}delete_cerita.php"
        const val UPDATE_CERITA = "${SERVER}update_cerita.php"

        // URLs untuk tabel wayang
        const val CREATE_WAYANG = "${SERVER}create_wayang.php"
        const val READ_WAYANG = "${SERVER}read_wayang.php"
        const val DELETE_WAYANG = "${SERVER}delete_wayang.php"
        const val UPDATE_WAYANG = "${SERVER}update_wayang.php"

        // Fungsi untuk mendapatkan URL baca untuk masing-masing tabel
        fun getReadUrlMenuTerjemahan(): String {
            return "${SERVER}menu_terjemahan/read.php"
        }

        fun getReadUrlMenuKamus(): String {
            return "${SERVER}menu_kamus/read_kamus.php"
        }

        fun getReadUrlMenuCerita(): String {
            return "${SERVER}menu_cerita/read_cerita.php"
        }

        fun getReadUrlMenuWayang(): String {
            return "${SERVER}menu_wayang/read_wayang.php"
        }
    }
}
