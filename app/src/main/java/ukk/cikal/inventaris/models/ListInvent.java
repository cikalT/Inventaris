package ukk.cikal.inventaris.models;

public class ListInvent {

    private String idInvent;
    private String namaInvent;
    private String kondisiInvent;
    private String ketInvent;
    private String jumlahInvent;
    private String idJenis;
    private String idRuang;
    private String tglRegis;
    private String kodeInvent;
    private String namaJenis;
    private String namaRuang;

    public ListInvent(String idInvent, String namaInvent, String kondisiInvent, String ketInvent, String jumlahInvent, String idJenis, String idRuang, String tglRegis, String kodeInvent, String namaJenis, String namaRuang) {
        this.idInvent = idInvent;
        this.namaInvent = namaInvent;
        this.kondisiInvent = kondisiInvent;
        this.ketInvent = ketInvent;
        this.jumlahInvent = jumlahInvent;
        this.idJenis = idJenis;
        this.idRuang = idRuang;
        this.tglRegis = tglRegis;
        this.kodeInvent = kodeInvent;
        this.namaJenis = namaJenis;
        this.namaRuang = namaRuang;
    }

    public String getIdInvent() {
        return idInvent;
    }

    public void setIdInvent(String idInvent) {
        this.idInvent = idInvent;
    }

    public String getNamaInvent() {
        return namaInvent;
    }

    public void setNamaInvent(String namaInvent) {
        this.namaInvent = namaInvent;
    }

    public String getKondisiInvent() {
        return kondisiInvent;
    }

    public void setKondisiInvent(String kondisiInvent) {
        this.kondisiInvent = kondisiInvent;
    }

    public String getKetInvent() {
        return ketInvent;
    }

    public void setKetInvent(String ketInvent) {
        this.ketInvent = ketInvent;
    }

    public String getJumlahInvent() {
        return jumlahInvent;
    }

    public void setJumlahInvent(String jumlahInvent) {
        this.jumlahInvent = jumlahInvent;
    }

    public String getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(String idJenis) {
        this.idJenis = idJenis;
    }

    public String getIdRuang() {
        return idRuang;
    }

    public void setIdRuang(String idRuang) {
        this.idRuang = idRuang;
    }

    public String getTglRegis() {
        return tglRegis;
    }

    public void setTglRegis(String tglRegis) {
        this.tglRegis = tglRegis;
    }

    public String getKodeInvent() {
        return kodeInvent;
    }

    public void setKodeInvent(String kodeInvent) {
        this.kodeInvent = kodeInvent;
    }

    public String getNamaJenis() {
        return namaJenis;
    }

    public void setNamaJenis(String namaJenis) {
        this.namaJenis = namaJenis;
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }
}
