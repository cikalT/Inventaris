package ukk.cikal.inventaris.helpers;

import android.net.Uri;

public class Api {

    private static String url = "http://192.168.43.170/inventaris/";
//    private static String url = "http://192.168.8.100/inventaris/";
    private static String ALLOWED_URI_CHARS = "/?&_!@#%*-=:";

    public static String getInvent = url + "get_invent.php";
    public static String getJenis = url + "get_jenis.php";
    public static String getRuang = url + "get_ruang.php";
    public static String getPegawai = url + "get_pegawai.php";
    public static String getCount = url + "count_all.php";
    public static String getReport = url + "get_report.php";
    public static String getOperator = url + "get_operator.php";

    private static String loginPengelola = url + "login_pengelola.php";
    private static String loginPegawai = url + "login_pegawai.php";
    private static String searchInvent = url + "search_invent.php";
    private static String addInvent = url + "add_invent.php";
    private static String deleteInvent = url + "delete_invent.php";
    private static String updateInvent = url + "update_invent.php";
    private static String idPegawai = url + "get_idpegawai.php";
    private static String addPeminjaman = url + "add_peminjaman.php";
    private static String cekKode = url + "cek_kode.php";
    private static String pengembalian = url + "pengembalian.php";
    private static String filterReport = url + "filter_report.php";
    private static String filterCount = url + "filter_count.php";
    private static String searchPegawai = url + "search_pegawai.php";
    private static String addPegawai = url + "add_pegawai.php";
    private static String addPegawaiNoNip = url + "add_pegawai_no_nip.php";
    private static String searchOperator = url + "search_operator.php";
    private static String addOperator = url + "add_operator.php";

    public static String getLoginPengelola(String username, String password) {
        return Uri.encode(loginPengelola +
                "?username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }

    public static String getLoginPegawai(String username, String password) {
        return Uri.encode(loginPegawai +
                "?username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }

    public static String getSearchInvent(String nama) {
        return Uri.encode(searchInvent + "?nama=" + nama, ALLOWED_URI_CHARS);
    }

    public static String getAddInvent(String nama, String kondisi, String ket, String jumlah, String jenis, String ruang, String idpetugas) {
        return Uri.encode(addInvent +
                "?nama=" + nama +
                "&kondisi=" + kondisi +
                "&ket=" + ket +
                "&jumlah=" + jumlah +
                "&jenis=" + jenis +
                "&ruang=" + ruang +
                "&idpetugas=" + idpetugas, ALLOWED_URI_CHARS);
    }

    public static String getDeleteInvent(String idinvent) {
        return Uri.encode(deleteInvent + "?idinvent=" + idinvent, ALLOWED_URI_CHARS);
    }

    public static String getUpdateInvent(String idinvent, String nama, String kondisi, String ket, String jumlah, String jenis, String ruang) {
        return Uri.encode(updateInvent +
                "?idinvent=" + idinvent +
                "&nama=" + nama +
                "&kondisi=" + kondisi +
                "&ket=" + ket +
                "&jumlah=" + jumlah +
                "&jenis=" + jenis +
                "&ruang=" + ruang, ALLOWED_URI_CHARS);
    }

    public static String getIdPegawai(String nama) {
        return Uri.encode(idPegawai + "?nama=" + nama, ALLOWED_URI_CHARS);
    }

    public static String getAddPeminjaman(String idpegawai, String idinvent, String jumlah, String tglpinjam, String tglkembali) {
        return Uri.encode(addPeminjaman +
                "?idpegawai=" + idpegawai +
                "&idinvent=" + idinvent +
                "&jumlah=" + jumlah +
                "&tglpinjam=" + tglpinjam +
                "&tglkembali=" + tglkembali, ALLOWED_URI_CHARS);
    }

    public static String getCekKode(String kode, String tgl) {
        return Uri.encode(cekKode +
                "?kode=" + kode +
                "&tgl=" + tgl, ALLOWED_URI_CHARS);
    }

    public static String getPengembalian(String kode) {
        return Uri.encode(pengembalian + "?kode=" + kode, ALLOWED_URI_CHARS);
    }

    public static String getFilterReport(String tglawal, String tglakhir) {
        return Uri.encode(filterReport +
                "?tglawal=" + tglawal +
                "&tglakhir=" + tglakhir, ALLOWED_URI_CHARS);
    }

    public static String getFilterCount(String tglawal, String tglakhir) {
        return Uri.encode(filterCount +
                "?tglawal=" + tglawal +
                "&tglakhir=" + tglakhir, ALLOWED_URI_CHARS);
    }

    public static String getSearchPegawai(String nama) {
        return Uri.encode(searchPegawai + "?nama=" + nama, ALLOWED_URI_CHARS);
    }

    public static String getAddPegawai(String nama, String nip, String alamat, String username, String password) {
        return Uri.encode(addPegawai +
                "?nama=" + nama +
                "&nip=" + nip +
                "&alamat=" + alamat +
                "&username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }

    public static String getAddPegawaiNoNip(String nama, String alamat, String username, String password) {
        return Uri.encode(addPegawaiNoNip +
                "?nama=" + nama +
                "&alamat=" + alamat +
                "&username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }

    public static String getSearchOperator(String nama) {
        return Uri.encode(searchOperator + "?nama=" + nama, ALLOWED_URI_CHARS);
    }

    public static String getAddOperator(String nama, String username, String password) {
        return Uri.encode(addOperator +
                "?nama=" + nama +
                "&username=" + username +
                "&password=" + password, ALLOWED_URI_CHARS);
    }
}
