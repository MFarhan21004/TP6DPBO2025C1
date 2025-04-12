import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360; // Lebar layar permainan
    int frameHeight = 640; // Tinggi layar permainan

    // Atribut gambar
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // Atribut pemain
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    Timer gameLoop; // Timer untuk permainan yang akan dipanggil setiap frame

    int gravity = 1; // Gaya gravitasi yang menarik pemain ke bawah

    // Atribut pipa
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    ArrayList<Pipe> pipes; // Menyimpan semua pipa yang ada

    Timer pipesCooldown; // Timer untuk menentukan kapan pipa akan muncul

    boolean isGameOver = false; // Status apakah permainan sudah berakhir
    int score = 0; // Skor permainan
    JLabel scoreLabel; // Label untuk menampilkan skor
    JLabel gameOverLabel; // Label untuk menampilkan pesan Game Over
    JLabel awalan; // Label untuk instruksi memulai permainan
    boolean gameStarted = false; // Status apakah permainan sudah dimulai
    boolean gameOver = false; // Status apakah permainan sudah berakhir
    private int lastPipeX = 400;  // Posisi X terakhir dari pipa yang ditempatkan

    // Konstruktor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this); // Menambahkan listener untuk input keyboard

        // Label untuk skor
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        this.setLayout(null);
        scoreLabel.setBounds(10, 10, 150, 30);
        this.add(scoreLabel);

        // Instruksi untuk memulai permainan
        awalan = new JLabel("Tekan SPASI untuk mulai");
        awalan.setFont(new Font("Arial", Font.BOLD, 20));
        awalan.setForeground(Color.YELLOW);
        awalan.setBounds(60, frameHeight / 2 - 50, 300, 30);  // Menempatkan label di tengah layar
        awalan.setVisible(true);  // Menampilkan instruksi pada awal permainan
        this.add(awalan);

        // Label untuk Game Over
        gameOverLabel = new JLabel("GAME OVER - Press 'R' to Restart");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setBounds(40, frameHeight / 2 - 30, 300, 30);
        gameOverLabel.setVisible(false);  // Sembunyikan label ini ketika game belum berakhir
        this.add(gameOverLabel);

        // Memuat gambar-gambar yang digunakan dalam permainan
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        // Membuat objek Player dan list pipa
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        // Timer untuk pembuatan pipa baru setiap detik
        pipesCooldown = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes(); // Tempatkan pipa baru setiap detik
            }
        });

        // Timer utama permainan yang akan dipanggil setiap frame (60 FPS)
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g); // Menggambar semua objek dalam permainan
    }

    // Fungsi untuk memeriksa kondisi permainan
    public void chekKondisi() {
        Rectangle playerBounds = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        // Memeriksa tabrakan dengan setiap pipa
        for (Pipe pipe : pipes) {
            Rectangle pipeBounds = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

            if (playerBounds.intersects(pipeBounds)) {
                triggerGameOver("Kamu Menabrak Pipa"); // Jika terjadi tabrakan, permainan berakhir
                return;
            }
        }

        // Periksa jika pemain jatuh ke bawah layar
        if (player.getPosY() + player.getHeight() > frameHeight) {
            triggerGameOver("Kamu Terjatuh"); // Jika pemain jatuh, permainan berakhir
        }
    }

    private void triggerGameOver(String reason) {
        gameLoop.stop(); // Berhenti menjalankan permainan
        pipesCooldown.stop(); // Berhenti menghasilkan pipa baru
        isGameOver = true;
        gameOver = true;

        // Menampilkan teks Game Over
        String gameOverText = "<html><center>GAME OVER - " + reason + "<br/>Score Akhir: " + score +
                "<br/>Tekan 'R' untuk Restart</center></html>";
        gameOverLabel.setText(gameOverText);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setVerticalAlignment(SwingConstants.CENTER);

        gameOverLabel.setBackground(Color.WHITE);
        gameOverLabel.setOpaque(true);

        FontMetrics fm = getFontMetrics(gameOverLabel.getFont());
        int labelWidth = fm.stringWidth(gameOverText);
        int labelHeight = fm.getHeight() * 3;

        int padding = 15;  // Padding untuk memberikan jarak antara teks dan border
        int labelX = (frameWidth - (labelWidth + padding * 2)) / 2;
        int labelY = (frameHeight - (labelHeight + padding * 2)) / 2;

        gameOverLabel.setBounds(labelX, labelY, labelWidth + padding * 2, labelHeight + padding * 2);
        gameOverLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));  // Border putih
        gameOverLabel.setBorder(BorderFactory.createCompoundBorder(
                gameOverLabel.getBorder(),
                BorderFactory.createEmptyBorder(padding, padding, padding, padding)
        ));

        gameOverLabel.setVisible(true); // Menampilkan label Game Over
    }

    // Fungsi untuk menggambar objek dalam permainan
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    // Fungsi untuk menggerakkan objek dalam permainan
    public void move() {
        if (!gameStarted || gameOver) return;

        player.setVelocityY(player.getVelocityY() + gravity); // Menambahkan gravitasi pada pemain
        player.setPosY(player.getPosY() + player.getVelocityY()); // Memperbarui posisi vertikal pemain

        if ((player.getPosY() + player.getHeight()) >= frameHeight) {
            System.out.println("Tabrakan terdeteksi! Pemain mencapai batas bawah layar.");
            chekKondisi();  // Periksa tabrakan setelah menggambar objek
        }

        // Cek tabrakan dan update skor
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);

            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX()); // Menggerakkan pipa ke kiri

            if (new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight())
                    .intersects(new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight()))) {
                chekKondisi();  // Periksa tabrakan
            }

            // Cek apakah pemain berhasil melewati pipa
            if (!pipe.isScored && pipe.getImage() == upperPipeImage && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.isScored = true;
                score++; // Tambah skor jika pemain melewati pipa
                scoreLabel.setText("Score: " + score);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Gerakkan objek
        repaint(); // Gambar ulang layar
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameStarted) {
                gameStarted = true;
                pipesCooldown.start();
                awalan.setVisible(false); // Sembunyikan instruksi memulai
            }

            if (!gameOver) {
                player.setVelocityY(-10); // Memberikan dorongan ke atas saat pemain menekan space
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            // Fungsi untuk mereset permainan setelah game over
            gameLoop.start();
            lastPipeX = 400;
            player.setPosY(playerStartPosY);
            player.setVelocityY(-0);
            pipes.clear();
            score = 0;
            scoreLabel.setText("Score: 0");
            isGameOver = false;
            gameOverLabel.setVisible(false);
            awalan.setVisible(true);
            gameOver = false;
            gameStarted = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    // Fungsi untuk menempatkan pipa baru secara acak
    public void placePipes() {
        int gap = 125; // Jarak vertikal antara pipa atas dan pipa bawah
        int minY = -pipeHeight + 100;  // Batas atas pipa atas
        int maxY = frameHeight - pipeHeight - gap - 50;  // Batas bawah pipa atas

        // Posisi Y acak untuk pipa atas
        int upperPipeY = minY + (int) (Math.random() * (maxY - minY));

        // Membuat pipa atas
        Pipe upperPipe = new Pipe(lastPipeX, upperPipeY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        // Membuat pipa bawah
        int lowerPipeY = upperPipeY + pipeHeight + gap;
        Pipe lowerPipe = new Pipe(lastPipeX, lowerPipeY, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);

        // Memperbarui posisi X untuk pipa berikutnya
        int pipeSpacing = 100; // Jarak horizontal antar pipa
        lastPipeX += pipeSpacing;  // Memindahkan posisi pipa
    }
}
