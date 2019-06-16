package com.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class jogo extends ApplicationAdapter {

	private int movimentoX = 0;
	private int movimentoY = 0;
	private SpriteBatch batch;
	private Texture passaro;
	private Texture fundo;

	//Atributos de Configurações
	private float larguraDispositivo;
	private float alturaDispositivo;

	@Override
	public void create () {
		//Gdx.app.log("create","Jogo Iniciado");
		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
		fundo = new Texture("fundo.png");
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();


	}

	@Override
	public void render () {

		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaro,movimentoX,movimentoY);
		movimentoX++;
		movimentoY++;
		batch.end();

		//contador ++;
		//Gdx.app.log("render","Jogo Renderizado: " + contador);
	}

	@Override
	public void dispose () {
		//Gdx.app.log("dispose","Descarte de Conteudos");
	}
}
