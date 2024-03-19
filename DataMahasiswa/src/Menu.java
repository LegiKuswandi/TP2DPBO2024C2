//  Saya Legi Kuswandi mengerjakan TP2 dalam mata kuliah DPBO
//  untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang
//  telah dispesifikasikan. Aamiin.

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame {
    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JRadioButton kostRadioButton;
    private JRadioButton rumahRadioButton;
    private JRadioButton asramaRadioButton;
    private JLabel TempatTinggal;

    private ButtonGroup tempatTinggalGroup; // Tambahkan ButtonGroup untuk mengelompokkan radio button

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));

        // Tambahkan radio button ke dalam ButtonGroup
        tempatTinggalGroup = new ButtonGroup();
        tempatTinggalGroup.add(kostRadioButton);
        tempatTinggalGroup.add(rumahRadioButton);
        tempatTinggalGroup.add(asramaRadioButton);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    int response = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        deleteData();
                    }
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();
                // simpan value textfield dan combo box
                String selectedNIM = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedjenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedTempatTinggal = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNIM);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedjenisKelamin);

                // Pilih radio button sesuai dengan tempat tinggal
                switch (selectedTempatTinggal) {
                    case "Kost":
                        kostRadioButton.setSelected(true);
                        break;
                    case "Rumah":
                        rumahRadioButton.setSelected(true);
                        break;
                    case "Asrama":
                        asramaRadioButton.setSelected(true);
                        break;
                    default:
                        kostRadioButton.setSelected(false);
                        rumahRadioButton.setSelected(false);
                        asramaRadioButton.setSelected(false);
                        break;
                }

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(560, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Tempat Tinggal"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            int i = 0;
            while (resultSet.next()){
                Object[] row = new Object[5];

                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("tempat_tinggal");
                temp.addRow(row);
                i++;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return temp; // return juga harus diganti
    }

    public void insertData() {
        // ambil value dari textfield, combobox, dan radio button
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String tempatTinggal = getSelectedTempatTinggal(); // Ambil nilai tempat tinggal dari radio button yang dipilih

        // Memeriksa apakah ada input yang kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || tempatTinggal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap lengkapi semua input!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Mengecek apakah NIM sudah ada dalam database atau belum
        String checkQuery = "SELECT COUNT(*) FROM mahasiswa WHERE nim = '" + nim + "'";
        ResultSet resultSet = database.selectQuery(checkQuery);
        try {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(null, "NIM sudah ada dalam database!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO mahasiswa values(null, '"+nim+"', '"+nama+"','"+jenisKelamin+"', '"+tempatTinggal+"');";
        database.InsertUpdateDelete(sql);
        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert Berhasil!");
        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String tempatTinggal = getSelectedTempatTinggal(); // Ambil nilai tempat tinggal dari radio button yang dipilih

        // Memeriksa apakah ada input yang kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || tempatTinggal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap lengkapi semua input!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', tempat_tinggal = '" + tempatTinggal + "' WHERE nim = " + nim + ";";
        database.InsertUpdateDelete(sql);
        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        String nim = nimField.getText();
        String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "';";
        database.InsertUpdateDelete(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete Berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // Set radio button menjadi tidak terpilih
        tempatTinggalGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    // Metode untuk mendapatkan nilai tempat tinggal dari radio button yang dipilih
    private String getSelectedTempatTinggal() {
        if (kostRadioButton.isSelected()) {
            return "Kost";
        } else if (rumahRadioButton.isSelected()) {
            return "Rumah";
        } else if (asramaRadioButton.isSelected()) {
            return "Asrama";
        } else {
            return ""; // Return default value jika tidak ada yang dipilih
        }
    }
}
