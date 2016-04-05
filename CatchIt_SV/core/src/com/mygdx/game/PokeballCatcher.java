package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PokeballCatcher extends ApplicationAdapter {
	final int TIMELIMIT = 30;
	SpriteBatch batch;
	Player myPlayer;
	ArrayList<Pokeball> pokeballs;
	BitmapFont font;
	boolean shouldRenderScores = false;

	int score = 0;
	double time = 0;
	List<String> scoresFromFile;

	@Override
	public void create() {
		batch = new SpriteBatch();
		myPlayer = new Player();
		pokeballs = new ArrayList<Pokeball>();
		for (int i = 0; i < 15; i++) {
			pokeballs.add(new Pokeball());
		}
		font = new BitmapFont();

		Gdx.input.setInputProcessor(new MyInputAdapter());

	}


	void loadScores() {

		try {
			List<String> lines = Files.readAllLines(Paths.get("score.txt"), StandardCharsets.UTF_8);
			this.scoresFromFile = lines;

		} catch (IOException e) {

		}

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		boolean shouldRenderStuff = time <= TIMELIMIT;
		batch.begin();
		myPlayer.draw(batch);

		if (shouldRenderStuff) {

			for (int i = 0; i < pokeballs.size(); i++) {
				pokeballs.get(i).draw(batch);
			}
			time += Gdx.graphics.getDeltaTime();
			if (time >= TIMELIMIT) {

				MyTextInputListener listener;
				listener = new MyTextInputListener();
				Gdx.input.getTextInput(listener, "Enter Name", "", "Enter your name");
			}
		} else {
			this.font.draw(batch, "THANK YOU FOR PLAYING! You Scored " + score, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 20);
			if (shouldRenderScores) {
				renderScores(batch);
			}
		}
		this.font.draw(batch, "Score : " + this.score, 10, Gdx.graphics.getHeight());
		this.font.draw(batch, "Time : " + String.format("%.2f", this.time), 10, Gdx.graphics.getHeight() - 30);
		batch.end();
	}
}