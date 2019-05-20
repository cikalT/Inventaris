package ukk.cikal.inventaris.models;

public class ListPegawai {

    private String idPegawai;
    private String namaPegawai;
    private String nip;
    private String alamat;
    private String username;
    private String password;

    public ListPegawai(String idPegawai, String namaPegawai, String nip, String alamat, String username, String password) {
        this.idPegawai = idPegawai;
        this.namaPegawai = namaPegawai;
        this.nip = nip;
        this.alamat = alamat;
        this.username = username;
        this.password = password;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
