package com.yc.chatroom.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.chatroom.entity.User;
import com.yc.chatroom.entity.YCCP;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class TalkView {

	protected Shell shell;
	private Text txtUname;
	private Text txtIP;
	private Text txtPort;
	private Text txtChatMsg;
	private Table tabUserList;
	private Text txtMsg;

	private Socket client ;
	private Scanner in;
	private PrintWriter out;

	private Button connBtn; // ���ӵİ�ť
	private Button breakBtn; // ���ӵİ�ť
	private Button sendMsgBtn; // ���ӵİ�ť

	private String nickname;

	private boolean flag=false;	//��ʶ���������ƽ�����Ϣ����


	public static void main(String[] args) {
		try {
			TalkView window = new TalkView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(TalkView.class, "/image/yc.ico"));
		shell.setSize(856, 619);
		shell.setText("\u6E90\u8FB0\u804A\u5929\u5BA4");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(shell, SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);

		Group group = new Group(sashForm, SWT.NONE);
		group.setText("\u8054\u63A5\u914D\u7F6E");

		Label label = new Label(group, SWT.NONE);
		label.setBounds(10, 38, 54, 18);
		label.setText("\u5462\u79F0:");

		txtUname = new Text(group, SWT.BORDER);
		txtUname.setText("cat");
		txtUname.setBounds(78, 38, 70, 18);

		Label lblip = new Label(group, SWT.NONE);
		lblip.setBounds(177, 38, 54, 18);
		lblip.setText("\u670D\u52A1\u5668IP:");

		txtIP = new Text(group, SWT.BORDER);
		txtIP.setText("127.0.0.1");
		txtIP.setBounds(260, 38, 167, 18);

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(446, 38, 54, 18);
		label_2.setText("\u7AEF\u53E3:");

		txtPort = new Text(group, SWT.BORDER);
		txtPort.setText("9090");
		txtPort.setBounds(521, 38, 70, 18);

		connBtn = new Button(group, SWT.NONE);


		connBtn.setBounds(614, 38, 72, 22);
		connBtn.setText("\u8054\u63A5");

		breakBtn = new Button(group, SWT.NONE);


		breakBtn.setEnabled(false);
		breakBtn.setBounds(719, 38, 72, 22);
		breakBtn.setText("\u65AD\u5F00");

		Group group_1 = new Group(sashForm, SWT.NONE);
		group_1.setText("\u804A\u5929\u8BB0\u5F55");
		group_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm_1 = new SashForm(group_1, SWT.NONE);

		Composite composite = new Composite(sashForm_1, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		txtChatMsg = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

		Group group_3 = new Group(sashForm_1, SWT.NONE);
		group_3.setText("\u5728\u7EBF\u7528\u6237\u5217\u8868");
		group_3.setLayout(new FillLayout(SWT.HORIZONTAL));

		tabUserList = new Table(group_3, SWT.BORDER | SWT.FULL_SELECTION);
		tabUserList.setHeaderVisible(true);
		tabUserList.setLinesVisible(true);

		TableColumn tableColumn = new TableColumn(tabUserList, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("\u5462\u79F0");

		TableColumn tblclmnIp = new TableColumn(tabUserList, SWT.NONE);
		tblclmnIp.setWidth(200);
		tblclmnIp.setText("IP");
		sashForm_1.setWeights(new int[] { 470, 369 });

		Group group_2 = new Group(sashForm, SWT.NONE);
		group_2.setText("\u804A\u5929\u4FE1\u606F");

		Label label_1 = new Label(group_2, SWT.NONE);
		label_1.setBounds(36, 29, 54, 18);
		label_1.setText("\u5185\u5BB9:");

		txtMsg = new Text(group_2, SWT.BORDER);

		txtMsg.setBounds(111, 23, 519, 70);

		sendMsgBtn = new Button(group_2, SWT.NONE);
		


		sendMsgBtn.setEnabled(false);
		sendMsgBtn.setBounds(700, 51, 72, 22);
		sendMsgBtn.setText("\u53D1\u9001");
		sashForm.setWeights(new int[] { 82, 384, 113 });
		doEvent();
		//����

		//�Ͽ�

		//����

	}

	public void doEvent(){
		//���ӷ�����
		connBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String ipStr = txtIP.getText();
				String portStr = txtPort.getText();
				InetAddress address ;			
				try {
					address = InetAddress.getByName(ipStr);
				} catch (UnknownHostException e1) {
					MessageBox mb = new MessageBox(shell,SWT.ICON_ERROR);
					mb.setText("���ӷ�������Ϣ");
					mb.setMessage("���������ӵ�ַ�����⣡��");
					mb.open();
					return;
				}
				try {
					client = new Socket(address, Integer.parseInt(portStr));	//�����������������ͨ��
					out = new PrintWriter(client.getOutputStream());	//ͨ���������
					in = new Scanner(client.getInputStream());	//ͨ����������
					connBtn.setEnabled(false);
					breakBtn.setEnabled(true);
					flag = true;
					doLogin();
					acceptMessages();	//������Ϣ
				} catch (NumberFormatException | IOException e1) {
					MessageBox mb = new MessageBox(shell,SWT.ICON_ERROR);
					mb.setText("���ӷ�������Ϣ");
					mb.setMessage("����������ʧ�ܣ����������������Ϣ����");
					mb.open();
					return;
				}

			}
		});

		//�Ͽ�������
		breakBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exitChatroom();
			}
		});
		//������Ϣ����
		txtMsg.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				sendMsgBtn.setEnabled("".intern() != txtMsg.getText().trim());
			}
		});
		//������Ϣ����
		sendMsgBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SendMessages();
			}
		});
		
		//���س���������Ϣ
		txtMsg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode == 13){
					SendMessages();
				}
			}
		});
		
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				if( client != null && client.isConnected()){
					exitChatroom();
				}
				shell.dispose();
			}
		});	
	}
	
	public void exitChatroom() {
		MessageBox mb = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_WARNING);
		mb.setText("���ӷ�������Ϣ");
		mb.setMessage("�Ƿ�Ͽ�������������ӣ���");

		if(mb.open() == SWT.YES){
			try {
				flag = false;
				client.close();
				connBtn.setEnabled(true);
				breakBtn.setEnabled(false);
				tabUserList.removeAll();
				txtChatMsg.setText("");
			} catch (IOException e1) {}
		}
	}

	//��������������Ϣ
	public void SendMessages() {
		YCCP yccp = new YCCP(YCCP.SEND_MSG,txtMsg.getText());
		String msg = new Gson().toJson(yccp);
		SendMessages(msg);
	}
	
	public void SendMessages(String msg) {
		
		out.println(msg);
		out.flush();
		txtMsg.setText("");
	}

	//���շ���������������Ϣ
	public void acceptMessages() {
		new Thread(){
			public void run() {
				while(flag){
					if(in.hasNextLine()){
						String line = in.nextLine();
						System.out.println(line);
						YCCP yccp = new Gson().fromJson(line, YCCP.class);
						//System.out.println("yccp ==============" + yccp);
						switch (yccp.getCode()) {
						case YCCP.USER_LIST:
							doUserList(yccp);
							break;
						case YCCP.SEND_MSG:
							doMsg(yccp);
							break;
						case YCCP.LOGIN_FAIL:
							doLoginFail(yccp);
							break;
						}					
					}
				}
			}
		}.start();		
	}

	//��¼ʧ�ܲ���
	private void doLoginFail(final YCCP yccp) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR);
				mb.setText("��¼��Ϣ");
				mb.setMessage(yccp.getContent());
				
				mb.open();
				try {
					flag = false;
					client.close();
					connBtn.setEnabled(true);
					breakBtn.setEnabled(false);
				} catch (IOException e) {
					e.printStackTrace();
				}		
			}
		});
	}

	//��Ϣ����
	private void doMsg(YCCP yccp) {
		final String line = yccp.getContent();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				txtChatMsg.setText(txtChatMsg.getText() + "\r\n" + line);
				//txtChatMsg.setText()�������߳��е���UI�̣߳���Ҫ����Display.getDefault().asyncExec() ����������û��Ȩ�ޣ��ᱨ��
			}
		});

	}

	//�û��б�Ĵ���
	private void doUserList(YCCP yccp) {
		Gson gson = new Gson();
		//TypeToken������gson�ṩ����������ת����������֧�ָ������ݼ�������ת����
		Type t = new TypeToken<List<User>>(){}.getType();	//json�������͵�ת�� 
		//System.out.println("t=========="+t);
		//t==========java.util.List<com.yc.chatroom.entity.User>
		//��Userʵ����ת����List������ʽ
		final List<User> users = gson.fromJson(yccp.getContent(), t);
		//System.out.println("users=========="+users);
		//��user��������ݸ���yccp.getContent()��ת����json��ʽ
		//users==========[com.yc.chatroom.entity.User@17ff5]
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				tabUserList.removeAll();
				for (User user : users) {
					TableItem ti = new TableItem(tabUserList, SWT.NONE);
					ti.setText(new String[] {user.getName(),user.getIp()});
				}
			}
		});
	};

	//��һ�����ӣ�����¼
	public void doLogin(){
		String username = txtUname.getText();
		YCCP yccp = new YCCP(YCCP.LOGIN, username);
		SendMessages(new Gson().toJson(yccp));
	}
}
