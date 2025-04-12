import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        // Memanggil metode untuk menampilkan form awal menggunakan SwingUtilities
        SwingUtilities.invokeLater(App::showStartForm);
    }

    public static void showStartForm() {
        int frameWidth = 360;
        int frameHeight = 640;

        // Membuat JFrame untuk form awal
        JFrame startFrame = new JFrame("Flappy Bird - Selamat Datang");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(frameWidth, frameHeight);
        startFrame.setLocationRelativeTo(null); // Menempatkan window di tengah layar
        startFrame.setResizable(false); // Menonaktifkan ukuran ulang window

        // Panel dengan background gambar
        JPanel panel = new JPanel() {
            // Memuat gambar background
            Image background = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this); // Menggambar gambar background
            }
        };
        panel.setLayout(null); // Mengatur layout panel menjadi null (tidak ada layout manager)

        // Membuat label judul utama
        JLabel titleLabel = new JLabel("SELAMAT DATANG");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 140, frameWidth, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Menengahkan teks
        panel.add(titleLabel);

        // Membuat label sub judul
        JLabel subTitle = new JLabel("di Dunia Flappy!");
        subTitle.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        subTitle.setForeground(Color.YELLOW);
        subTitle.setBounds(0, 180, frameWidth, 30);
        subTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subTitle);

        // Membuat tombol mulai
        JButton startButton = new JButton("Main Sekarang");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(255, 204, 0));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false); // Menghilangkan border ketika tombol dipilih
        startButton.setBounds((frameWidth - 180) / 2, 300, 180, 45); // Menempatkan tombol di tengah
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Mengubah cursor menjadi tangan saat hover
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        // Menambahkan efek perubahan warna saat mouse hover
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(Color.WHITE);
                startButton.setForeground(new Color(255, 153, 0)); // Mengubah warna saat hover
            }
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(new Color(255, 204, 0));
                startButton.setForeground(Color.BLACK); // Mengembalikan warna saat mouse keluar
            }
        });

        // Aksi tombol untuk membuka window permainan
        startButton.addActionListener(e -> {
            startFrame.dispose(); // Menutup frame awal
            openGameWindow(); // Membuka jendela permainan
        });

        // Membuat tombol keluar
        JButton exitButton = new JButton("Keluar");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBounds((frameWidth - 140) / 2, 370, 140, 40); // Menempatkan tombol di bawah tombol mulai
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        // Menambahkan efek perubahan warna saat mouse hover pada tombol keluar
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.DARK_GRAY);
                exitButton.setForeground(Color.RED); // Mengubah warna saat hover
            }

            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.RED);
                exitButton.setForeground(Color.WHITE); // Mengembalikan warna saat mouse keluar
            }
        });

        // Aksi tombol untuk keluar dari aplikasi
        exitButton.addActionListener(e -> {
            System.exit(0); // Menutup aplikasi
        });

        // Menambahkan tombol ke dalam panel
        panel.add(startButton);
        panel.add(exitButton);
        startFrame.setContentPane(panel); // Menetapkan panel sebagai konten frame
        startFrame.setVisible(true); // Menampilkan frame
    }

    // Membuka window permainan
    public static void openGameWindow() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Membuat dan menambahkan objek permainan
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack(); // Mengatur ukuran frame sesuai dengan konten
        flappyBird.requestFocus(); // Memberikan fokus pada objek permainan
        frame.setVisible(true); // Menampilkan frame permainan
    }
}
