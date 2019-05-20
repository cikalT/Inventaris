package ukk.cikal.inventaris.models;

public class ListReport {

    private String idPeminjaman;
    private String idInvent;
    private String idPegawai;
    private String jumlah;
    private String tglPinjam;
    private String tglKembali;
    private String statusPinjam;
    private String kodePinjaman;
    private String namaInvent;
    private String namaPegawai;

    public ListReport(String idPeminjaman, String idInvent, String idPegawai, String jumlah, String tglPinjam, String tglKembali, String statusPinjam, String kodePinjaman, String namaInvent, String namaPegawai) {
        this.idPeminjaman = idPeminjaman;
        this.idInvent = idInvent;
        this.idPegawai = idPegawai;
        this.jumlah = jumlah;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.statusPinjam = statusPinjam;
        this.kodePinjaman = kodePinjaman;
        this.namaInvent = namaInvent;
        this.namaPegawai = namaPegawai;
    }

    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String getIdInvent() {
        return idInvent;
    }

    public void setIdInvent(String idInvent) {
        this.idInvent = idInvent;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTglPinjam() {
        return tglPinjam;
    }

    public void setTglPinjam(String tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    public String getStatusPinjam() {
        return statusPinjam;
    }

    public void setStatusPinjam(String statusPinjam) {
        this.statusPinjam = statusPinjam;
    }

    public String getKodePinjaman() {
        return kodePinjaman;
    }

    public void setKodePinjaman(String kodePinjaman) {
        this.kodePinjaman = kodePinjaman;
    }

    public String getNamaInvent() {
        return namaInvent;
    }

    public void setNamaInvent(String namaInvent) {
        this.namaInvent = namaInvent;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }
}
