package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Chansey_Game extends ApplicationAdapter {
	final int TIMELIMIT = 30;
	SpriteBatch batch;
	Player myPlayer;
	ArrayList<Voltorb> pokeballs;
	ArrayList<Egg> eggs;
	BitmapFont font ;
	boolean shouldRenderScores = false;
	int score = 0;
	double time = 0;
	List<String> scoresFromFile;
	private Sound shock_sound = null;
	private Music backgroundMusic = null;


	@Override
	public void create () {
		batch = new SpriteBatch();
		myPlayer = new Player();

		//number of voltorbs array
		pokeballs = new ArrayList<Voltorb>() ;
		for (int i=0 ; i < 9 ; i ++) {
			pokeballs.add(new Voltorb());
		}
		//number of eggs array
		eggs = new ArrayList<Egg>() ;
		for (int i=0 ; i < 15 ; i ++) {
			eggs.add(new Egg());
		}
		//loading shock sound effect
		font = new BitmapFont();
		shock_sound = Gdx.audio.newSound(Gdx.files.internal("core/assets/electric.mp3"));
		Gdx.input.setInputProcessor(new MyInputAdapter());

		//loading and playing background music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("core/assets/pokemon-egg_emergency.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

	}

	//loading the scores stored in txt file
	void loadScores() {

		try {
			//reading from scores file
			List<String> lines = Files.readAllLines(Paths.get("core/assets/score.txt"), StandardCharsets.UTF_8);
			this.scoresFromFile = lines;

		} catch (IOException e) {

		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//if the time is less than 30 seconds, continue the game.
		boolean shouldRenderStuff = time <= TIMELIMIT;
		batch.begin();
		myPlayer.draw(batch);

		if (shouldRenderStuff) {

			//drawing voltorbs
			for (int i = 0; i < pokeballs.size(); i++) {
				pokeballs.get(i).draw(batch);
			}
			//drawing eggs
			for (int i = 0; i < eggs.size(); i++) {
				eggs.get(i).draw(batch);
			}
			//end the game once time reaches 30 seconds
			time += Gdx.graphics.getDeltaTime();
			if (time >= TIMELIMIT) {
				//stopping the background music
				backgroundMusic.stop();
				MyTextInputListener listener;
				listener = new MyTextInputListener();
				//prompting user for name
				Gdx.input.getTextInput(listener, "Enter Name", "", "Enter your name");
			}
		} else {
			// display player's score on game screen
			this.font.draw(batch, "Time Up!! You Scored " + score, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 20);
			if (shouldRenderScores) {
				renderScores(batch);
			}
		}
		this.font.draw(batch, "Score : " + this.score, 10, Gdx.graphics.getHeight());
		this.font.draw(batch, "Time : " + String.format("%.2f", this.time), 10, Gdx.graphics.getHeight() - 30);
		batch.end();

		// checking if ball touches player
		if (shouldRenderStuff) {
			for (int i = 0; i < pokeballs.size(); i++) {
				//if voltorb touches player, lose one point
				if (pokeballs.get(i).isColliding(myPlayer)) {
					score--;
					//play shock sound on impact
					shock_sound.play();
					pokeballs.get(i).recycleObject();

				} else {
					pokeballs.get(i).physics();
				}
			}

			for (int i = 0; i < eggs.size(); i++) {
				//if egg touches player (catches), add one point
				if (eggs.get(i).isColliding(myPlayer)) {
					score++;
					eggs.get(i).recycleObject();

				} else {
					eggs.get(i).physics();

				}
			}
			myPlayer.updateMotion();
		}
	}
	void renderScores(SpriteBatch batch ) {
		List<String> scores = this.scoresFromFile;
		//display previous scores from .txt file
		this.font.draw(batch, String.format("%1$-20s" + "Score", "Name"), Gdx.graphics.getWidth() / 2 - 40, Gdx.graphics.getHeight() - 50);
		for (int i = 0; i < scores.size(); i++) {
			String[] tokens= scores.get(i).split(";");
			//The first item is the name, the second is the score
			this.font.draw(batch, String.format("%1$-20s"+ tokens[1], tokens[0]) , Gdx.graphics.getWidth()/2 - 40, Gdx.graphics.getHeight() - 20 - 30*(i+2));
		}


	}
	//method to save scores on local text file to server
	void saveToserver(String object) {
		Socket clientSocket = null;
		try {
			clientSocket = new Socket("localhost", 9655);

			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

			outToServer.writeBytes(object + '\n');

			clientSocket.close();
		} catch (IOException e) {
			//testing socket connection
			System.out.println("Server isn't running!");
		}

	}

	public class MyTextInputListener implements Input.TextInputListener {
		@Override
		public void input (String text) {
			List<String> lines = new ArrayList<String>();

			try {
				lines = Files.readAllLines(Paths.get("core/assets/score.txt"), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//adding player's name, separated by score
			String toAdd = text+";"+score;
			lines.add(toAdd);

			try {
				//writing to scores file to record and save scores
				PrintWriter writer = new PrintWriter("core/assets/score.txt");
				for (int i = 0; i < lines.size(); i++) {
					if ( ! lines.get(i).equals("") )
						writer.write(lines.get(i));
					writer.write("\n");
				}
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				saveToserver(toAdd);
				loadScores();
				shouldRenderScores = true;

			}
		}

		@Override
		public void canceled () {
		}
	}


	private class MyInputAdapter extends InputAdapter {
		@Override
		public boolean keyDown(int keycode)
		{
			switch (keycode)
			{	//moving player based on user input (arrow keys)
				case Input.Keys.LEFT:
					myPlayer.setLeftMove(true);
					break;
				case Input.Keys.RIGHT:
					myPlayer.setRightMove(true);
					break;
			}
			return true;
		}

		@Override
		public boolean keyUp(int keycode)
		{
			switch (keycode)
			{
				case Input.Keys.LEFT:
					myPlayer.setLeftMove(false);
					break;
				case Input.Keys.RIGHT:
					myPlayer.setRightMove(false);
					break;
			}
			return true;
		}

	}
}
