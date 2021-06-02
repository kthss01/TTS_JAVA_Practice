package jlayer_example1_notworking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class MyPlayer {
	public static final int BUFFER_SIZE = 44100000;
	private Decoder decoder;
	private AudioDevice out;
	private ArrayList<Sample> samples;
	private short[][] musibuffers;
	private int size;

	public MyPlayer(String path) {
		Open(path);
	}

	public boolean IsInvalid() {
		return (decoder == null || out == null || samples == null || !out.isOpen());
	}

	public boolean Open(String path) {
		Close();
		try {
			decoder = new Decoder();
			out = FactoryRegistry.systemRegistry().createAudioDevice();
			samples = new ArrayList<Sample>(BUFFER_SIZE);
			size = 0;
			out.open(decoder);
			GetSamples(path);
//			musibuffers = GetMusicBuffers();
		} catch (JavaLayerException e) {
			decoder = null;
			out = null;
			return false;
		}
		return true;
	}

	public void Close() {
		if ((out != null) && !out.isOpen())
			out.close();
		size = 0;
		samples = null;
		out = null;
		decoder = null;
	}

	protected boolean GetSamples(String path) {
		if (IsInvalid())
			return false;
		try {
			Header header;
			SampleBuffer sb;
			FileInputStream in = new FileInputStream(path);
			Bitstream bitstream = new Bitstream(in);

			if ((header = bitstream.readFrame()) == null)
				return false;

			while (size < BUFFER_SIZE && header != null) {
				sb = (SampleBuffer) decoder.decodeFrame(header, bitstream);
				samples.add(new Sample(sb.getBuffer(), sb.getBufferLength()));
				size++;
				bitstream.closeFrame();
				header = bitstream.readFrame();
			}

		} catch (FileNotFoundException e) {
			return false;
		} catch (BitstreamException e) {
			return false;
		} catch (DecoderException e) {
			return false;
		}
		return true;
	}

	public void Play() {
		if (IsInvalid())
			return;

		try {
			for (int i = 0; i < size; i++) {
				short[] buffers = samples.get(i).getBuffer();
				out.write(samples.get(i).getBuffer(), 0, samples.get(i).getSize());
			}
			out.flush();
		} catch (JavaLayerException e) {
		}
	}
	
	public static void main(String[] args) {
		MyPlayer mp = new MyPlayer("1622630526371.mp3");
		mp.Play();
		mp.Close();
	}
}
