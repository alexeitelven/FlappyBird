package com.flappybird;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class jogo extends ApplicationAdapter {


    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;

    //Atributos de Configurações
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float variacao = 0;
    private float gravidade = 2;
    private float posicaoInivialVerticalPassaro = 0; /*Y*/
    private float posicaoCanoHorizontal;
    private float espacoEntreCanos;


    @Override
    public void create() {
        inicializarTexturas();
        inicializaObjetos();
    }

    @Override
    public void render() {
        verificaEstadoJogo();
        desenharTexturas();

    }

    private void verificaEstadoJogo(){


        /* Movimentar Canos*/
        posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() *200;


        /*Aplica evento de toque na tela*/
        boolean toqueTela = Gdx.input.justTouched();
        if (toqueTela == true) {
            //Gdx.app.log("Toque","Toque na tela");
            gravidade = -25;
        }
        /*Aplica gravidade no pássaro*/
        if (posicaoInivialVerticalPassaro > 0 || toqueTela == true) {
            posicaoInivialVerticalPassaro = posicaoInivialVerticalPassaro - gravidade;
        }

        variacao += Gdx.graphics.getDeltaTime() * 10;

        /*variação faz a troca da imagem do vetor de passaros - bater as asas*/
        /*testa se a variação é maior que 2 para iniciar a animação dnv - passaro1,passaro2,passaro3*/
        if (variacao > 3) {
            variacao = 0;
        }

        gravidade++;


    }
    private void desenharTexturas() {
        batch.begin();

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        /*mostra na tela o passaro animado*/
        /*casting variação para conseguir utilizar a variação float pegando somente os numeros inteiros*/
        batch.draw(passaros[(int) variacao], 30, posicaoInivialVerticalPassaro);
        batch.draw(canoBaixo,posicaoCanoHorizontal,(alturaDispositivo/2 - canoBaixo.getHeight()) - (espacoEntreCanos/2));
        batch.draw(canoTopo,posicaoCanoHorizontal,alturaDispositivo/2 + (espacoEntreCanos/2));


        batch.end();
    }

    private void inicializarTexturas() {
        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");
        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo_maior.png");
        canoTopo = new Texture("cano_topo_maior.png");
    }

    private void inicializaObjetos() {

        batch = new SpriteBatch();

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInivialVerticalPassaro = alturaDispositivo / 2;
        posicaoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 150;
    }

    @Override
    public void dispose() {
        //Gdx.app.log("dispose","Descarte de Conteudos");
    }
}
