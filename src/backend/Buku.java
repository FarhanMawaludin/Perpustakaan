package backend;

import java.util.ArrayList;
import java.sql.*;

public class Buku {
    private int idBuku;
    private Kategori kategori = new Kategori();
    private String judul;
    private String penerbit;
    private String penulis;

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public Buku() {
    }

    public Buku(Kategori kategori, String judul, String penerbit, String penulis) {
        this.kategori = kategori;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }
    
    public Buku getById(int id) {
        Buku buku = new Buku();
        String query = "SELECT b.idbuku, b.judul, b.penerbit, b.penulis, b.idkategori, k.nama " +
                       "FROM buku b " +
                       "LEFT JOIN kategori k ON b.idkategori = k.idkategori " +
                       "WHERE b.idbuku = '" + id + "'";

        ResultSet rs = DBHelper.selectQuery(query);

        try {
            if (rs.next()) {
                buku.setIdBuku(rs.getInt("idbuku"));
                buku.getKategori().setIdkategori(rs.getInt("idkategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buku;
    }

public ArrayList<Buku> getAll() {
        ArrayList<Buku> listBuku = new ArrayList<>();
        String query = "SELECT b.idbuku, b.judul, b.penerbit, b.penulis, b.idkategori, k.nama " +
                       "FROM buku b " +
                       "LEFT JOIN kategori k ON b.idkategori = k.idkategori";

        ResultSet rs = DBHelper.selectQuery(query);

        try {
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("idbuku"));
                buku.getKategori().setIdkategori(rs.getInt("idkategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));

                listBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBuku;
    }

public ArrayList<Buku> search(String keyword) {
    ArrayList<Buku> ListBuku = new ArrayList<>();
    ResultSet rs = DBHelper.selectQuery("SELECT " +
            " b.idbuku AS idbuku, " +
            " b.judul AS judul, " +
            " b.penerbit AS penerbit, " +
            " b.penulis AS penulis, " +
            " k.idkategori AS idkategori, " +
            " k.nama AS nama, " +
            " FROM buku b " +
            " LEFT JOIN kategori k ON b.idkategori = k.idkategori " +
            " WHERE b.judul LIKE '%" + keyword + "%' " +
            " OR b.penerbit LIKE '%" + keyword + "%' " +
            " OR b.penulis LIKE '%" + keyword + "%'");

    try {
        while (rs.next()) {
            Buku buku = new Buku();
            buku.setIdBuku(rs.getInt("idbuku"));
            buku.getKategori().setIdkategori(rs.getInt("idkategori"));
            buku.getKategori().setNama(rs.getString("nama"));
            buku.setJudul(rs.getString("judul"));
            buku.setPenerbit(rs.getString("penerbit"));
            buku.setPenulis(rs.getString("penulis"));

            ListBuku.add(buku);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return ListBuku;
}

public void save() {
    if (getById(idBuku).getIdBuku() == 0) {
        String SQL = "INSERT INTO buku (judul, idkategori, penulis, penerbit) VALUES(" +
                " '" + this.judul + "', " +
                " '" + this.getKategori().getIdkategori() + "', " +
                " '" + this.penulis + "', " +
                " '" + this.penerbit + "' " +
                " )";
        this.idBuku = DBHelper.insertQueryGetId(SQL);
    } else {
        String SQL = "UPDATE buku SET " +
                " judul = '" + this.judul + "', " +
                " idkategori = '" + this.getKategori().getIdkategori() + "', " +
                " penulis = '" + this.penulis + "', " +
                " penerbit = '" + this.penerbit + "' " +
                " WHERE idbuku = '" + this.idBuku + "'";
        DBHelper.executeQuery(SQL);
    }
}

public void delete() {
    String SQL = "DELETE FROM buku WHERE idbuku = '" + this.idBuku + "'";
    DBHelper.executeQuery(SQL);
}


}
