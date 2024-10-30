import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;

public class BankingSystem {
    private static JFrame loginFrame; // Giriş ekranı için JFrame
    private static JPanel loginPanel; // Giriş ekranı için JPanel
    private static JFrame bankingFrame; // Banka menüsü ekranı için JFrame
    private static JPanel bankingPanel; // Banka menüsü ekranı için JPanel
    private static String loggedInUserId; // Giriş yapmış kullanıcının ID'si

    public static void main(String[] args) {
        createLoginFrame(); // Giriş ekranını oluştur
    }

    private static void createLoginFrame() {
        loginFrame = new JFrame("Giriş veya Kayıt Ol"); // Giriş ekranı başlığı
        loginFrame.setSize(900, 500); // Giriş ekranı boyutu
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatma işlemi

        loginPanel = new JPanel(); // Yeni bir JPanel oluştur
        loginFrame.add(loginPanel); // Paneli ekle
        placeLoginComponents(); // Giriş bileşenlerini yerleştir

        loginFrame.setVisible(true); // Giriş ekranını görünür yap
    }

    private static void placeLoginComponents() {
        // Banka ikonunu ekle
        JLabel bacj=new JLabel("\uD83C\uDFE6");
        bacj.setFont(new Font("serif", Font.BOLD, 150));
        bacj.setForeground(Color.darkGray);
        bacj.setBounds(700,280,200,200);
        loginPanel.add(bacj);

        loginPanel.setLayout(null); // Panel yerleşimini manuel olarak ayarla

        // Başlık ve hoşgeldiniz metni ekle
        JLabel jLabel1=new JLabel();
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 30));
        jLabel1.setText("SELAM BANK");
        jLabel1.setBounds(350, 40, 200, 30);
        JLabel jLabel2=new JLabel();
        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 20));
        jLabel2.setText("HOŞGELDİNİZ");
        jLabel2.setBounds(380, 5, 200, 30);

        loginPanel.add(jLabel1);
        loginPanel.add(jLabel2);

        // Kullanıcı adı etiketini ve metin alanını ekle
        JLabel userLabel = new JLabel("Kullanıcı Adı :");
        userLabel.setBounds(100, 200, 80, 25);
        loginPanel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(350, 200, 219, 30);
        loginPanel.add(userText);

        // Şifre etiketini ve metin alanını ekle
        JLabel passwordLabel = new JLabel("Şifre :");
        passwordLabel.setBounds(100, 260, 80, 25);
        loginPanel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(350, 260, 219, 30);
        loginPanel.add(passwordText);

        // Giriş ve kayıt ol butonlarını ekle
        JButton loginButton = new JButton("Giriş");
        loginButton.setBounds(350, 320, 100, 25);
        loginPanel.add(loginButton);
        loginButton.setBackground(new java.awt.Color(143, 143, 143));

        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.setBounds(470, 320, 100, 25);
        loginPanel.add(registerButton);
        registerButton.setBackground(new java.awt.Color(143, 143, 143));

        // Giriş butonu için eylem dinleyicisi ekle
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText(); // Kullanıcı adını al
                String password = new String(passwordText.getPassword()); // Şifreyi al
                if (user.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kullanıcı adı veya şifre boş olamaz.");
                } else if (authenticate(user, password)) {
                    loggedInUserId = user; // Giriş yapan kullanıcının ID'sini ayarla
                    loginFrame.dispose(); // Giriş ekranını kapat
                    createBankingFrame(); // Banka menüsünü aç
                } else {
                    JOptionPane.showMessageDialog(null, "Geçersiz kullanıcı adı veya şifre");
                }
            }
        });

        // Kayıt ol butonu için eylem dinleyicisi ekle
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText(); // Kullanıcı adını al
                String password = new String(passwordText.getPassword()); // Şifreyi al
                if (user.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kullanıcı adı veya şifre boş olamaz.");
                } else if (register(user, password)) {
                    JOptionPane.showMessageDialog(null, "Kayıt başarılı. Şimdi giriş yapabilirsiniz.");
                } else {
                    JOptionPane.showMessageDialog(null, "Kayıt başarısız. Lütfen farklı bir kullanıcı adıyla tekrar deneyin.");
                }
            }
        });
    }

    // Kullanıcı kimlik doğrulama yöntemi
    private static boolean authenticate(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            boolean isValid = resultSet.next();
            connection.close();
            return isValid;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Kullanıcı kayıt yöntemi
    private static boolean register(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStatement.setString(1, username);
            ResultSet checkResultSet = checkStatement.executeQuery();
            if (checkResultSet.next()) {
                connection.close();
                return false; // Kullanıcı adı zaten var
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, balance) VALUES (?, ?, 0)");
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            connection.close();
            return rowsInserted > 0; // Kayıt başarılıysa true döner
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Banka menüsü ekranını oluşturma yöntemi
    private static void createBankingFrame() {
        bankingFrame = new JFrame("Banka Menüsü"); // Banka menüsü başlığı
        bankingFrame.setSize(900, 500); // Banka menüsü boyutu
        bankingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatma işlemi

        bankingPanel = new JPanel(); // Yeni bir JPanel oluştur
        bankingFrame.add(bankingPanel); // Paneli ekle
        placeBankingComponents(); // Banka bileşenlerini yerleştir

        bankingFrame.setVisible(true); // Banka menüsünü görünür yap
    }

    private static void placeBankingComponents() {
        bankingPanel.setLayout(null); // Panel yerleşimini manuel olarak ayarla

        // Başlık ve kullanıcı adı etiketi ekle
        JLabel titleLabel = new JLabel("HOŞGELDİNİZ" );
        JLabel user=new JLabel(loggedInUserId);

        // Para ikonunu ekle
        JLabel bacj=new JLabel("\uD83D\uDCB6\uD83D\uDC09");
        bacj.setFont(new Font("serif", Font.BOLD, 150));
        bacj.setForeground(Color.darkGray);
        bacj.setBounds(700,280,200,200);

        titleLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 30));
        titleLabel.setBounds(350, 7, 400, 30);
        user.setBounds(410,50,170,20);
        user.setFont(new Font("Segoe UI Black", 0, 25));

        // Bakiye sorgulama, para yatırma, para çekme ve transfer butonlarını ekle
        JButton balanceButton = new JButton("Bakiye Sorgula");
        balanceButton.setBounds(200, 170, 200, 30);
        balanceButton.setBackground(new java.awt.Color(143, 143, 143));

        JButton depositButton = new JButton("Para Yatır");
        depositButton.setBounds(200, 240, 200, 30);
        depositButton.setBackground(new java.awt.Color(143, 143, 143));

        JButton withdrawButton = new JButton("Para Çek");
        withdrawButton.setBounds(500, 240, 200, 30);
        withdrawButton.setBackground(new java.awt.Color(143, 143, 143));

        JButton transferButton = new JButton("Para Transferi");
        transferButton.setBounds(500, 170, 200, 30);
        transferButton.setBackground(new java.awt.Color(143, 143, 143));

        JButton backButton = new JButton("Geri");
        backButton.setBounds(20, 420, 80, 25);
        backButton.setBackground(new java.awt.Color(143, 143, 143));

        // Bileşenleri ekle
        bankingPanel.add(titleLabel);
        bankingPanel.add(balanceButton);
        bankingPanel.add(depositButton);
        bankingPanel.add(withdrawButton);
        bankingPanel.add(transferButton);
        bankingPanel.add(backButton);
        bankingPanel.add(user);

        // Bakiye sorgulama butonu için eylem dinleyicisi ekle
        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
                    PreparedStatement balanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                    balanceStatement.setString(1, loggedInUserId);
                    ResultSet resultSet = balanceStatement.executeQuery();

                    if (resultSet.next()) {
                        BigDecimal balance = resultSet.getBigDecimal("balance");
                        JOptionPane.showMessageDialog(null, "Bakiyeniz : $" + balance + " $");
                    } else {
                        JOptionPane.showMessageDialog(null, "Bakiye alınamadı. Lütfen tekrar deneyin.");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Para yatırma butonu için eylem dinleyicisi ekle
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String depositAmount = JOptionPane.showInputDialog("Yatırılacak miktarı giriniz : ");

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
                    PreparedStatement depositStatement = connection.prepareStatement("UPDATE users SET balance = balance + ? WHERE username = ?");
                    depositStatement.setBigDecimal(1, new BigDecimal(depositAmount));
                    depositStatement.setString(2, loggedInUserId);

                    int rowsUpdated = depositStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, depositAmount + " $ tutarında yatırma işlemi başarılı.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Yatırma işlemi başarısız. Lütfen tekrar deneyin.");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Para çekme butonu için eylem dinleyicisi ekle
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String withdrawalAmountStr = JOptionPane.showInputDialog("Çekilecek miktarı giriniz : ");

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");

                    // Mevcut bakiyeyi al
                    PreparedStatement balanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                    balanceStatement.setString(1, loggedInUserId);
                    ResultSet resultSet = balanceStatement.executeQuery();

                    if (resultSet.next()) {
                        BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                        BigDecimal withdrawalAmount = new BigDecimal(withdrawalAmountStr);

                        // Çekilmek istenen miktarın mevcut bakiyeden fazla olup olmadığını kontrol et
                        if (withdrawalAmount.compareTo(currentBalance) > 0) {
                            JOptionPane.showMessageDialog(null, "Yetersiz bakiye. Çekmek istediğiniz tutar mevcut bakiyenizden fazla.");
                        } else {
                            // Çekme işlemini gerçekleştir
                            PreparedStatement withdrawalStatement = connection.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
                            withdrawalStatement.setBigDecimal(1, withdrawalAmount);
                            withdrawalStatement.setString(2, loggedInUserId);

                            int rowsUpdated = withdrawalStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, withdrawalAmount + " $ tutarında çekme işlemi başarılı.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Çekme işlemi başarısız. Lütfen tekrar deneyin.");
                            }
                        }
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Para transferi butonu için eylem dinleyicisi ekle
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String transferAmount = JOptionPane.showInputDialog("Transfer edilecek miktarı giriniz :");
                String recipientUsername = JOptionPane.showInputDialog("Alıcı kullanıcının adını giriniz :");

                if (transferAmount == null || recipientUsername == null || transferAmount.isEmpty() || recipientUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Transfer miktarı veya alıcı adı boş olamaz.");
                    return;
                }

                try {
                    BigDecimal transferAmountDecimal = new BigDecimal(transferAmount);

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");

                    // Gönderenin bakiyesini kontrol et
                    PreparedStatement checkBalanceStatement = null;
                    PreparedStatement senderStatement = null;
                    PreparedStatement recipientStatement = null;
                    ResultSet rs = null;

                    try {
                        checkBalanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                        checkBalanceStatement.setString(1, loggedInUserId);
                        rs = checkBalanceStatement.executeQuery();

                        if (rs.next()) {
                            BigDecimal senderBalance = rs.getBigDecimal("balance");

                            if (senderBalance.compareTo(transferAmountDecimal) >= 0) {
                                // Transfer işlemini gerçekleştir
                                connection.setAutoCommit(false); // İşlemi başlat

                                senderStatement = connection.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
                                senderStatement.setBigDecimal(1, transferAmountDecimal);
                                senderStatement.setString(2, loggedInUserId);

                                int senderRowsUpdated = senderStatement.executeUpdate();

                                recipientStatement = connection.prepareStatement("UPDATE users SET balance = balance + ? WHERE username = ?");
                                recipientStatement.setBigDecimal(1, transferAmountDecimal);
                                recipientStatement.setString(2, recipientUsername);

                                int recipientRowsUpdated = recipientStatement.executeUpdate();

                                if (senderRowsUpdated > 0 && recipientRowsUpdated > 0) {
                                    connection.commit(); // İşlemi tamamla
                                    JOptionPane.showMessageDialog(null, transferAmount + " $ tutarında " + recipientUsername + " kullanıcısına transfer başarılı.");
                                } else {
                                    connection.rollback(); // İşlemi geri al
                                    JOptionPane.showMessageDialog(null, "Transfer başarısız. Lütfen detayları kontrol edip tekrar deneyin.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Yetersiz bakiye. Lütfen tekrar deneyin.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Böyle bir kullanıcı bulunamadı.");
                        }
                    } catch (SQLException ex) {
                        if (connection != null) {
                            try {
                                connection.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
                    } finally {
                        if (rs != null) try { rs.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                        if (checkBalanceStatement != null) try { checkBalanceStatement.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                        if (senderStatement != null) try { senderStatement.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                        if (recipientStatement != null) try { recipientStatement.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                        if (connection != null) try { connection.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Geçersiz miktar formatı. Lütfen geçerli bir sayı giriniz.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + ex.getMessage());
                }
            }
        });

        // Geri butonu için eylem dinleyicisi ekle
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bankingFrame.dispose(); // Banka menüsünü kapat
                createLoginFrame(); // Giriş ekranını tekrar aç
            }
        });
    }
}


