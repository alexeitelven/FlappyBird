package com.flappybird;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class jogo extends ApplicationAdapter {


    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Texture gameOver;


    //Formas para Colisão
    private ShapeRenderer shapeRenderer;
    private Circle circuloPassaro;
    private Rectangle retanguloCanoCima;
    private Rectangle retanguloCanoBaixo;


    //Atributos de Configurações
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float variacao = 0;
    private float gravidade = 2;
    private float posicaoInicialVerticalPassaro = 0; /*Y*/
    private float posicaoCanoHorizontal;
    private float posicaoCanoVertical;
    private float espacoEntreCanos;
    private Random random;
    private int pontos = 0;
    private boolean passouCano = false;
    private int estadoJogo = 0;


    //Exibição de Textos
    BitmapFont textoPontuacao;
    BitmapFont textoReiniciar;
    BitmapFont textoMelhorPontuacao;


    @Override
    public void create() {
        inicializarTexturas();
        inicializaObjetos();
    }

    @Override
    public void render() {
        verificaEstadoJogo();
        validarPontos();
        desenharTexturas();
        detectarColisoes();

    }

    private void verificaEstadoJogo() {

        /*
            0 - Jogo Inicial - Passaro parado
            1 - Começa o jogo
            2 - Colidiu - Perdeu!
        */
        boolean toqueTela = Gdx.input.justTouched();
        if (estadoJogo == 0) {
            /*Aplica evento de toque na tela*/

            if (toqueTela == true) {
                //Gdx.app.log("Toque","Toque na tela");
                gravidade = -15;
                estadoJogo = 1;
            }

        } else if (estadoJogo == 1) {

            /*Aplica evento de toque na tela*/

            if (toqueTela == true) {
                //Gdx.app.log("Toque","Toque na tela");
                gravidade = -15;
            }

            /* Movimentar Canos*/
            posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
            if (posicaoCanoHorizontal < -canoTopo.getWidth()) {
                posicaoCanoHorizontal = larguraDispositivo;
                posicaoCanoVertical = random.nextInt(400) - 200;
                passouCano = false;
            }
            /*Aplica gravidade no pássaro*/
            if (posicaoInicialVerticalPassaro > 0 || toqueTela == true) {
                posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;
            }



            gravidade++;


        } else if (estadoJogo == 2) {


        }


    }

    private void detectarColisoes() {

        //FORMAS PARA COLISAO
        //Passaro
        circuloPassaro.set((float) (50 + passaros[0].getWidth() / 2), (posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2), passaros[0].getWidth() / 2);
        //Cano Baixo
        retanguloCanoBaixo.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical,
                canoBaixo.getWidth(), canoBaixo.getHeight());

        //Cano CIMA
        retanguloCanoCima.set(posicaoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical,
                canoTopo.getWidth(), canoTopo.getHeight());

        boolean colidiuCanoCima = Intersector.overlaps(circuloPassaro, retanguloCanoCima);
        boolean colidiuCanoBaixo = Intersector.overlaps(circuloPassaro, retanguloCanoBaixo);

        if (colidiuCanoCima || colidiuCanoBaixo) {
            Gdx.app.log("colisão", "COLIDIU ESSA MERDA!");
            estadoJogo = 2;

        }


        /*
        // DESENHANDO AS FORMAS GEOMETRICAS - para entender a colisao
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        // Passaro
        shapeRenderer.circle((float)(50 + passaros[0].getWidth() /2) ,(posicaoInicialVerticalPassaro + passaros[0].getHeight() /2),passaros[0].getWidth() /2);
        //Cano TOPO
        shapeRenderer.rect(posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical,canoTopo.getWidth(), canoTopo.getHeight());
        //Cano Baixo
        shapeRenderer.rect(posicaoCanoHorizontal,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos/2 + posicaoCanoVertical,canoBaixo.getWidth(),canoBaixo.getHeight());


        shapeRenderer.end();
        */


    }

    private void desenharTexturas() {
        batch.begin();

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        /*mostra na tela o passaro animado*/
        /*casting variação para conseguir utilizar a variação float pegando somente os numeros inteiros*/
        batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);

        batch.draw(canoBaixo, posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
        batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);

        //Desenha Pontação
        textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 150);

        batch.end();
    }

    public void validarPontos() {

        if (posicaoCanoHorizontal < 50 - passaros[0].getWidth()) { // Passou da possição do passaro
            if (!passouCano) {
                pontos++;
                passouCano = true;
            }
        }
        variacao += Gdx.graphics.getDeltaTime() * 10;

        /*variação faz a troca da imagem do vetor de passaros - bater as asas*/
        /*testa se a variação é maior que 2 para iniciar a animação dnv - passaro1,passaro2,passaro3*/
        if (variacao > 3) {
            variacao = 0;
        }

    }

    private void inicializarTexturas() {
        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        fundo = new Texture("fundo.png");

        canoBaixo = new Texture("cano_baixo_maior.png");
        canoTopo = new Texture("cano_topo_maior.png");

        gameOver = new Texture ("game_over.png");



    }

    private void inicializaObjetos() {

        batch = new SpriteBatch();
        random = new Random();
        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInicialVerticalPassaro = alturaDispositivo / 2;
        posicaoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 200;

        //Configuração dos textos

        textoPontuacao = new BitmapFont();
        textoPontuacao.setColor(Color.WHITE);
        textoPontuacao.getData().setScale(10);

        textoReiniciar = new BitmapFont();
        textoReiniciar.setColor(Color.WHITE);


        //Formas para Colisões
        shapeRenderer = new ShapeRenderer();
        circuloPassaro = new Circle();
        retanguloCanoBaixo = new Rectangle();
        retanguloCanoCima = new Rectangle();

    }

    @Override
    public void dispose() {
        //Gdx.app.log("dispose","Descarte de Conteudos");
    }
}
