import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
public class LoginView extends JFrame implements ActionListener {
    public LoginView () {
        initComponents();
        setVisible (true);
    }
    private void initComponents() {
        setTitle("Đăng Nhập Hệ Thống");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel();
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel footerPanel = new JPanel();

        headerPanel.setBackground(new Color(87, 184, 70));
        JLabel titleLabel = new JLabel("Đăng Nhập");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        panel.add(headerPanel, BorderLayout.NORTH);

        GridBagConstraints gridBagConstraints;

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(lblUsername, gridBagConstraints);

        txtUsername = new JTextField(20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(txtUsername, gridBagConstraints);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(lblPassword, gridBagConstraints);

        txtPassword = new JPasswordField(20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(txtPassword, gridBagConstraints);

        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogin.setBackground(new Color(87, 184, 70));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(20, 10, 10, 10);
        centerPanel.add(btnLogin, gridBagConstraints);

        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnRegister = new JButton("Chưa có tài khoản? Đăng kí ngay");
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRegister.setForeground(new Color(0, 132, 255));
        btnRegister.addActionListener(this);
        footerPanel.add(btnRegister);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        add(panel);
        pack();
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
    }
    public void setEventLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập và mật khẩu!");
            } else {
                if (loginSuccessful(username, password)) {
                    this.dispose(); // Ẩn cửa sổ đăng nhập
                    new MainFrame(); // Hiển thị giao diện chính của ứng dụng
//                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }
    private boolean loginSuccessful(String username, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                // Mã hoá mật khẩu nhập vào và so sánh với mật khẩu đã mã hoá trong cơ sở dữ liệu
                return hashPassword(password).equals(hashedPassword);
            } else {
                return false; // Tên đăng nhập không tồn tại trong cơ sở dữ liệu
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra tên đăng nhập và mật khẩu: " + ex.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    public void switchRegister(){
        RegisterView registerView = new RegisterView(); // Khởi tạo giao diện đăng kí mới
        this.setVisible(false); // Ẩn giao diện đăng nhập hiện tại
        registerView.setVisible(true); // Hiển thị giao diện đăng kí mới
    }
    public void actionPerformed (ActionEvent e) {
        if(e.getSource ()==btnLogin){
            setEventLogin ();
        } else if (e.getSource ()==btnRegister) {
            switchRegister ();
        }
    }
    private String hashPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private JLabel lblForgotPassword;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
}
