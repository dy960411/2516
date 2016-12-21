package a;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SwingTest extends JFrame {
 // 确认按钮
 private JButton b1 = new JButton("按钮");
 // 入力文本框
 private JTextField txt = new JTextField(10);
 // 显示文本域
 private JTextArea area = new JTextArea(2, 20);
    private JPanel jp = new JPanel();
 
 // 事件类
 class SwingTestListener implements ActionListener {
  public void actionPerformed(ActionEvent e) {
   area.setText(txt.getText());
   txt.setText("");
  }
 }
 
 public SwingTest(String frameName) {
  super(frameName);
  // 按下按钮动作响应
  b1.addActionListener(new SwingTestListener());
  // 按下ENTER键响应
  txt.addActionListener(new SwingTestListener());
  
  setLayout(new FlowLayout());
  add(txt);
  add(b1);
  jp.setBorder(new TitledBorder("TextArea"));
  jp.add(area);
  add(jp);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setSize(300 , 150);
  setLocation(500, 500);
  setVisible(true);
 }
 
 public static void main(String[] args) {
  new SwingTest("SwingTest");
 }
}