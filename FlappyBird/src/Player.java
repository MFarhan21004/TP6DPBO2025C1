import java.awt.*;

public class Player {
    private int posX;  // Posisi X pemain
    private int posY;  // Posisi Y pemain
    private int width; // Lebar pemain
    private int height; // Tinggi pemain
    private Image image; // Gambar pemain (misalnya burung)
    private int velocityY; // Kecepatan vertikal pemain

    // Constructor untuk inisialisasi posisi, ukuran, gambar, dan kecepatan pemain
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = -0;  // Kecepatan vertikal awal pemain (0 berarti tidak ada gerakan vertikal awal)
    }

    // Getter dan setter untuk setiap atribut pemain

    public int getPosX() {
        return posX;  // Mendapatkan posisi X pemain
    }

    public void setPosX(int posX) {
        this.posX = posX;  // Mengatur posisi X pemain
    }

    public int getPosY() {
        return posY;  // Mendapatkan posisi Y pemain
    }

    public void setPosY(int posY) {
        this.posY = posY;  // Mengatur posisi Y pemain
    }

    public int getWidth() {
        return width;  // Mendapatkan lebar pemain
    }

    public void setWidth(int width) {
        this.width = width;  // Mengatur lebar pemain
    }

    public int getHeight() {
        return height;  // Mendapatkan tinggi pemain
    }

    public void setHeight(int height) {
        this.height = height;  // Mengatur tinggi pemain
    }

    public Image getImage() {
        return image;  // Mendapatkan gambar pemain
    }

    public void setImage(Image image) {
        this.image = image;  // Mengatur gambar pemain
    }

    public int getVelocityY() {
        return velocityY;  // Mendapatkan kecepatan vertikal pemain
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;  // Mengatur kecepatan vertikal pemain
    }

}
