import java.awt.*;

public class Pipe {
    private int posX;  // Posisi X pipa
    private int posY;  // Posisi Y pipa
    private int width; // Lebar pipa
    private int height; // Tinggi pipa
    private Image image; // Gambar pipa
    private int velocityX; // Kecepatan pergerakan horizontal pipa
    boolean passed = false;  // Status apakah pipa sudah dilewati oleh pemain
    boolean scored = false;  // Status apakah pipa sudah memberi poin
    public boolean isScored = false;  // Status pipa yang sudah dihitung poinnya oleh game

    // Constructor untuk inisialisasi posisi, ukuran, gambar, dan kecepatan pipa
    public Pipe(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityX = -4; // Pipa bergerak ke kiri (kecepatan negatif)
        this.passed = false;
    }

    // Getter dan setter untuk setiap atribut pipa
    public int getPosX() { return posX; }  // Mendapatkan posisi X pipa
    public void setPosX(int posX) { this.posX = posX; }  // Mengatur posisi X pipa

    public int getPosY() { return posY; }  // Mendapatkan posisi Y pipa
    public void setPosY(int posY) { this.posY = posY; }  // Mengatur posisi Y pipa

    public int getWidth() { return width; }  // Mendapatkan lebar pipa
    public void setWidth(int width) { this.width = width; }  // Mengatur lebar pipa

    public int getHeight() { return height; }  // Mendapatkan tinggi pipa
    public void setHeight(int height) { this.height = height; }  // Mengatur tinggi pipa

    public Image getImage() { return image; }  // Mendapatkan gambar pipa
    public void setImage(Image image) { this.image = image; }  // Mengatur gambar pipa

    public int getVelocityX() { return velocityX; }  // Mendapatkan kecepatan horizontal pipa
    public void setVelocityX(int velocityX) { this.velocityX = velocityX; }  // Mengatur kecepatan horizontal pipa

    public boolean isPassed() { return passed; }  // Mendapatkan status apakah pipa sudah dilewati pemain
    public void setPassed(boolean passed) { this.passed = passed; }  // Mengatur status dilewati atau tidak pipa

    public boolean isScored() {  // Mendapatkan status apakah pipa sudah dihitung skor
        return scored;
    }

    public void setScored(boolean scored) {  // Mengatur status pipa yang sudah memberi skor
        this.scored = scored;
    }
}
