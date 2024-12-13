/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author ASUS
 */
import java.util.ArrayList;
import java.sql.*;

public class Peminjaman {
    private int idPeminjaman;
    private Anggota anggota = new Anggota();
    private Buku buku = new Buku();
    private Pegawai pegawai = new Pegawai();
    private String tanggalPinjam;
    private String tanggalKembali;

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }
    
    

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Peminjaman() {}

    public Peminjaman(Anggota anggota, Buku buku, String tanggalPinjam, String tanggalKembali) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    public Peminjaman getById(int id) {
    Peminjaman peminjaman = new Peminjaman();
    String query = "SELECT p.idpeminjaman, p.tanggalpinjam, p.tanggalkembali, " +
                   "a.idanggota, a.nama AS nama_anggota, " +
                   "b.idbuku, b.judul AS judul_buku, " +
                   "pg.idpegawai, pg.nama AS nama_pegawai " +
                   "FROM peminjaman p " +
                   "LEFT JOIN anggota a ON p.idanggota = a.idanggota " +
                   "LEFT JOIN buku b ON p.idbuku = b.idbuku " +
                   "LEFT JOIN pegawai pg ON p.idpegawai = pg.idpegawai " +
                   "WHERE p.idpeminjaman = '" + id + "'";

    ResultSet rs = DBHelper.selectQuery(query);

    try {
        if (rs.next()) {
            peminjaman.setIdPeminjaman(rs.getInt("idpeminjaman"));

            // Buat objek Anggota
            Anggota anggota = new Anggota().getById(rs.getInt("idanggota"));
            peminjaman.setAnggota(anggota);

            // Buat objek Buku
            Buku buku = new Buku().getById(rs.getInt("idbuku"));
            peminjaman.setBuku(buku);

            // Buat objek Pegawai
            Pegawai pegawai = new Pegawai().getById(rs.getInt("idpegawai"));
            peminjaman.setPegawai(pegawai);

            // Set tanggal pinjam dan kembali
            peminjaman.setTanggalPinjam(rs.getString("tanggalpinjam"));
            peminjaman.setTanggalKembali(rs.getString("tanggalkembali"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return peminjaman;
}

    public ArrayList<Peminjaman> getAll() {
    ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
    String query = "SELECT p.idpeminjaman, p.tanggalpinjam, p.tanggalkembali, " +
                   "a.idanggota, a.nama AS nama_anggota, " +
                   "b.idbuku, b.judul AS judul_buku, " +
                   "pg.idpegawai, pg.nama AS nama_pegawai " +
                   "FROM peminjaman p " +
                   "LEFT JOIN anggota a ON p.idanggota = a.idanggota " +
                   "LEFT JOIN buku b ON p.idbuku = b.idbuku " +
                   "LEFT JOIN pegawai pg ON p.idpegawai = pg.idpegawai ORDER BY P.idpeminjaman DESC";

    ResultSet rs = DBHelper.selectQuery(query);

    try {
        while (rs.next()) {
            Peminjaman peminjaman = new Peminjaman();
            peminjaman.setIdPeminjaman(rs.getInt("idpeminjaman"));

            // Buat objek Anggota dan Buku
            Anggota anggota = new Anggota().getById(rs.getInt("idanggota"));
            Buku buku = new Buku().getById(rs.getInt("idbuku"));
            
            // Buat objek Pegawai
            Pegawai pegawai = new Pegawai().getById(rs.getInt("idpegawai"));

            peminjaman.setAnggota(anggota);
            peminjaman.setBuku(buku);
            peminjaman.setTanggalPinjam(rs.getString("tanggalpinjam"));
            peminjaman.setTanggalKembali(rs.getString("tanggalkembali"));

            // Set Pegawai ke Peminjaman
            peminjaman.setPegawai(pegawai);

            listPeminjaman.add(peminjaman);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listPeminjaman;
}


    public ArrayList<Peminjaman> search(String keyword) {
    ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
    String query = "SELECT p.idpeminjaman, p.tanggalpinjam, p.tanggalkembali, " +
                   "a.idanggota, a.nama AS nama_anggota, " +
                   "b.idbuku, b.judul AS judul_buku, " +
                   "p.idpegawai " + // Tambahkan kolom idpegawai
                   "FROM peminjaman p " +
                   "LEFT JOIN anggota a ON p.idanggota = a.idanggota " +
                   "LEFT JOIN buku b ON p.idbuku = b.idbuku " +
                   "WHERE p.idpeminjaman LIKE '%" + keyword + "%' " +
                   "OR b.idbuku LIKE '%" + keyword + "%' " +
                   "OR b.judul LIKE '%" + keyword + "%' " +
                   "OR p.tanggalpinjam LIKE '%" + keyword + "%' " +
                   "OR a.nama LIKE '%" + keyword + "%'"; // Tambahkan pencarian berdasarkan nama anggota

    ResultSet rs = DBHelper.selectQuery(query);

    try {
        while (rs.next()) {
            Peminjaman peminjaman = new Peminjaman();
            peminjaman.setIdPeminjaman(rs.getInt("idpeminjaman"));

            Anggota anggota = new Anggota().getById(rs.getInt("idanggota"));
            Buku buku = new Buku().getById(rs.getInt("idbuku"));
            
            Pegawai pegawai = new Pegawai().getById(rs.getInt("idpegawai"));

            peminjaman.setAnggota(anggota);
            peminjaman.setBuku(buku);
            peminjaman.setTanggalPinjam(rs.getString("tanggalpinjam"));
            peminjaman.setTanggalKembali(rs.getString("tanggalkembali"));
            peminjaman.setPegawai(pegawai);

            listPeminjaman.add(peminjaman);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listPeminjaman;
}




public void save() {
    String SQL;
    SQL = "INSERT INTO peminjaman (idanggota, idbuku, tanggalpinjam, tanggalkembali, idpegawai) VALUES ("
        + "'" + this.getAnggota().getIdanggota() + "', "
        + "'" + this.getBuku().getIdBuku() + "', "
        + "'" + this.getTanggalPinjam() + "', "
        + (this.getTanggalKembali().isEmpty() ? "NULL" : "'" + this.getTanggalKembali() + "'") + ", "
        + this.getPegawai().getIdpegawai() + ")";
    
    this.idPeminjaman = DBHelper.insertQueryGetId(SQL);
}


    public void delete() {
        String query = "DELETE FROM peminjaman WHERE idpeminjaman = " + this.idPeminjaman;
        DBHelper.executeQuery(query);
    }

    public void setTanggalPinjam(java.util.Date tanggalPinjam) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
public void update() {
    String SQL;
    SQL = "UPDATE peminjaman SET "
            + "idpegawai = " + this.getPegawai().getIdpegawai() + ", "
            + "tanggalkembali = '" + this.getTanggalKembali() + "' "
            + "WHERE idpeminjaman = '" + this.idPeminjaman + "'";

    DBHelper.executeQuery(SQL);
}
}
