package ukk.cikal.inventaris.models;

public class ListOperator {

    private String idPetugas;
    private String namaPetugas;
    private String username;
    private String password;

    public ListOperator(String idPetugas, String namaPetugas, String username, String password) {
        this.idPetugas = idPetugas;
        this.namaPetugas = namaPetugas;
        this.username = username;
        this.password = password;
    }

    public String getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(String idPetugas) {
        this.idPetugas = idPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
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
