import javax.swing.*;

public class MainFrame {
    public MainFrame() {
        // Tạo frame
        JFrame frame = new JFrame("Welcome to");

        // Thiết lập kích thước của frame
        frame.setSize(300, 200);

        // Đặt chế độ đóng khi nhấp vào nút đóng cửa sổ
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo nhãn chứa chữ "Welcome to"
        JLabel label = new JLabel("Welcome to");
        // Căn giữa văn bản trong nhãn
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Thêm nhãn vào frame
        frame.getContentPane().add(label);

        // Hiển thị frame
        frame.setVisible(true);
    }
}
