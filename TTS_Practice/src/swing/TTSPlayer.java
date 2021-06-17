package swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jlayer_sample.JLayerPlayer;
import naver_clova_sample.NaverClovaTTS;
import naver_clova_sample.TTSSpeaker;
import javax.swing.JSpinner;
import javax.swing.JSlider;

public class TTSPlayer {

	private JFrame frame;
	private JTextField textFieldText;

	private NaverClovaTTS tts = new NaverClovaTTS();

	private JLayerPlayer player = new JLayerPlayer();
	private JTextArea textAreaText;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TTSPlayer window = new TTSPlayer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//		TTSPlayer window = new TTSPlayer();
//		window.frame.setVisible(true);
//		window.initSpeak();
	}

	public TTSPlayer() {
		initialize();
		initSpeak();
	}

	private void initSpeak() {
		// 간단하게 로딩을 위해서 인사하기
		String str = "안녕하세요 이번 프로젝트에서는 네이버 CLOVA를 이용하여 TTS를 구현해보았고 JLayer를 이용해 mp3 파일을 자바에서 실행해보았습니다.";
		textAreaText.setText(str);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("tts 스레드 시작");
				tts.TTS(str);
				player.play(tts.lastPlay());
			}
		});
		thread.start();
	}

	private void initialize() {
		frame = new JFrame();
		
		frame.setTitle("네이버 CLOVA TTS for Java");
		
		frame.setBounds(100, 90, 592, 635);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.out.println("프레임 종료 중 호출");
				System.out.println("tts 파일 삭제 시작");
				tts.removeFiles(); // download 한 파일들 제거
				System.out.println("tts 파일 삭제 완료");
			}
		});

		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 400, 475, 150);
		frame.getContentPane().add(scrollPane);

		DefaultListModel<String> model = new DefaultListModel<String>();
		for (TTSSpeaker speaker : tts.getSpeakers().values()) {
			model.addElement(speaker.getKorName());
		}

		JList<String> list = new JList<String>();
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setModel(model);
		list.setSelectedIndex(2);
		scrollPane.setViewportView(list);

		textFieldText = new JTextField();
		textFieldText.setBounds(100, 550, 395, 45);
		frame.getContentPane().add(textFieldText);
		textFieldText.setColumns(10);

		JButton btnSendText = new JButton("보내기");
		btnSendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldText.getText();
				textAreaText.setText(text);

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						System.out.println("tts 스레드 시작");

						String speaker = list.getSelectedValue();
						// System.out.println(speaker); // test

						tts.setSpeaker(tts.selectSpeaker(speaker));

						tts.TTS(text);
						player.play(tts.lastPlay());
					}
				});
				thread.start();
			}
		});
		btnSendText.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnSendText.setBounds(495, 550, 80, 45);
		frame.getContentPane().add(btnSendText);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(28, 37, 60, (int) (0.7 * 256)));
		panel.setBounds(0, 0, 576, 400);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panelSpeak = new JPanel();
		panelSpeak.setBounds(45, 10, 490, 160);
		ImagePanel imagePanel_1 = new ImagePanel("./resources/images/speech_bubble3.png", panelSpeak.getSize());
		panelSpeak.add(imagePanel_1);
		panel.add(panelSpeak);
		panelSpeak.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(85, 30, 320, 70);
		panelSpeak.add(scrollPane_1);

		textAreaText = new JTextArea();
		textAreaText.setLineWrap(true);
		scrollPane_1.setViewportView(textAreaText);
		textAreaText.setFont(new Font("맑은 고딕", Font.PLAIN, 13));

		JPanel panelImage = new JPanel();
		panelImage.setBounds(195, 185, 250, 200);
		panelImage.setLayout(null);
		ImagePanel imagePanel = new ImagePanel("./resources/images/amongus.png", panelImage.getSize());
		panelImage.add(imagePanel);
		panel.add(panelImage);

		JSlider sliderVolume = new JSlider();
		sliderVolume.setValue(tts.getVolume());

		sliderVolume.setPaintTicks(true);
		sliderVolume.setPaintLabels(true);
		sliderVolume.setMajorTickSpacing(5);
		sliderVolume.setMinorTickSpacing(-5);
		sliderVolume.setMaximum(5);
		sliderVolume.setMinimum(-5);
		sliderVolume.setValue(0);
		sliderVolume.setOrientation(SwingConstants.VERTICAL);
		sliderVolume.setBounds(495, 245, 40, 130);

		sliderVolume.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int val = sliderVolume.getValue();
				tts.setVolume(val);
			}
		});

		panel.add(sliderVolume);

		JSlider sliderPitch = new JSlider();
		sliderPitch.setValue(tts.getPitch());

		sliderPitch.setPaintTicks(true);
		sliderPitch.setPaintLabels(true);
		sliderPitch.setMajorTickSpacing(5);
		sliderPitch.setMinorTickSpacing(-5);
		sliderPitch.setMaximum(5);
		sliderPitch.setMinimum(-5);
		sliderPitch.setValue(0);
		sliderPitch.setBounds(15, 326, 135, 35);

		sliderPitch.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int val = sliderPitch.getValue();
				tts.setPitch(val);
			}
		});

		panel.add(sliderPitch);

		JSlider sliderSpeed = new JSlider();
		sliderSpeed.setValue(tts.getSpeed());

		sliderSpeed.setPaintTicks(true);
		sliderSpeed.setPaintLabels(true);
		sliderSpeed.setMajorTickSpacing(5);
		sliderSpeed.setMinorTickSpacing(-5);
		sliderSpeed.setValue(0);
		sliderSpeed.setMaximum(5);
		sliderSpeed.setMinimum(-5);
		sliderSpeed.setBounds(15, 245, 135, 35);

		sliderSpeed.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int val = sliderSpeed.getValue();
				tts.setSpeed(val);
			}
		});

		panel.add(sliderSpeed);

		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblSpeed.setBounds(15, 215, 57, 15);
		panel.add(lblSpeed);

		JLabel lblPitch = new JLabel("Pitch");
		lblPitch.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblPitch.setBounds(15, 301, 57, 15);
		panel.add(lblPitch);

		JPanel panelVolumeImg = new JPanel();
		panelVolumeImg.setBackground(Color.WHITE);
		panelVolumeImg.setLayout(null);
		panelVolumeImg.setBounds(500, 210, 30, 30);
		ImagePanel imagePanel_2 = new ImagePanel("./resources/images/speaker.png", panelVolumeImg.getSize());
		panelVolumeImg.add(imagePanel_2);
		panel.add(panelVolumeImg);

		JButton btnReadText = new JButton("읽어오기");
		btnReadText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputTextDialog dialog = new InputTextDialog(textFieldText);

				dialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						super.windowClosed(e);

						String text = textFieldText.getText();
						textAreaText.setText(text);

						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								System.out.println("tts 스레드 시작");
								String speaker = list.getSelectedValue();
//								System.out.println(speaker); // test

								tts.setSpeaker(tts.selectSpeaker(speaker));

								tts.TTS(text);
								player.play(tts.lastPlay());
							}
						});
						thread.start();
					}
				});
			}
		});
		btnReadText.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnReadText.setBounds(0, 550, 100, 45);
		frame.getContentPane().add(btnReadText);

		JLabel lblChoiceSpeaker = new JLabel("목소리");
		lblChoiceSpeaker.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		lblChoiceSpeaker.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoiceSpeaker.setBounds(12, 410, 78, 60);
		frame.getContentPane().add(lblChoiceSpeaker);

		JLabel lblChoiceSpeaker_1 = new JLabel("선택");
		lblChoiceSpeaker_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoiceSpeaker_1.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		lblChoiceSpeaker_1.setBounds(12, 480, 78, 60);
		frame.getContentPane().add(lblChoiceSpeaker_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 400, 100, 150);
		frame.getContentPane().add(panel_1);
	}
}
