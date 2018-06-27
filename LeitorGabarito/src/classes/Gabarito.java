/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;


import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author aaaaaaaaaaaaaaaaaaaa
 */
public class Gabarito {

    private int ra[];
    private char questoes[];
   // private int countQuestoesTeste; // campo para testes

  /*  public static void main(String[] args) {

        Gabarito gabarito = new Gabarito();
        
        boolean leu = gabarito.ler("Gabaritos//GABARITOS04082017_0018.jpg"); 
        if(!leu){
            System.out.println("Não foi possível ler o arquivo");
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(gabarito.getRa(i));
        }

        for (int i = 0; i < 90; i++) {
            System.out.println("Alternativa" + (i + 1) + ":"+gabarito.getQuestoes(i));
        }

    }*/

    public Gabarito() {
        // inicializa com tamanho padrão
        this(10, 90);
    }

    public Gabarito(int tamanhoRa, int tamanhoQuestoes) {
        // inicializa com tamanho especifico
        ra = new int[tamanhoRa];
        questoes = new char[tamanhoQuestoes];
        for(int i = 0; i < questoes.length; i++){
            questoes[i] = ' ';
        }
    }

    public void setRa(int index, int argRa) {
        if (index < 0 || index > ra.length) {
            System.out.println("Index out of bounds " + index);
            return;
        }

        ra[index] = argRa;
    }

    public int getRa() {
        String retorno = "";
        for(int i = 0; i < ra.length;i++){
            if(ra[i] == -1){
                return 0;
            }
            retorno += "" + ra[i];
        }
        return Integer.parseInt(retorno);

    }

    public void setQuestoes(int index, char argQuestoes) {
        if (index < 0 || index > questoes.length) {
            System.out.println("Index out of bounds " + index);
            return;
        }

        argQuestoes = Character.toUpperCase(argQuestoes);

        questoes[index] = argQuestoes;
    }

    public char getQuestoes(int index) {
        if (index < 0 || index > questoes.length) {
            System.out.println("Index out of bounds " + index);
            return ' ';
        }

        return questoes[index];
    }
    
    
        
    private BufferedImage alinhar(BufferedImage image){
        
        // valor utilizado para girar a imagem de acordo com a difenreça
        // das coordenadas Y dos quadrados superiores de marcação da folha
        final double CALIBRACAO = 33; 

      //  int threshold = calculateThreshold(image);
      // System.out.println(threshold);
        // Limiarizando a imagem para uma imagem binária com base no threshold
        image = getBinaryImage(image, 140);
        
        int diffXY[] = getDiffXYQuadradosSuperiores(image);
        
        if(diffXY[0] == 0){
            System.out.println("Não foram encontrados os dois quadrados de marcação");
            return null;
        }
        
        int diffX = diffXY[0];
        int diffY = diffXY[1];
        
        // girando 180 graus caso a imagem esteja de ponta cabeça
        // obtendo as coords Y dos quadrados novamente
        if(diffX < 900){
          image = rotate(image, 180);
          diffY = getDiffXYQuadradosSuperiores(image)[1];
        }       
        
        // calculando o anguloRotacao para girar a imagem
        double anguloRotacao = diffY / CALIBRACAO;
        // girando a imagem para alinhá-la
        image = rotate(image, anguloRotacao);
  
        return image;
    }
    

    

    private BufferedImage getBinaryImage(BufferedImage image, int threshold) {
        //  Preto: -16777216
        //  Branco: -1
        
        // binarizando a imagem de acordo com o threshold
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                //int escalaCinzaPixel = image.getRGB(x, y) & 0xFF;
                 int escalaVermelho = (image.getRGB(x, y) & 0x00ff0000) >> 16;
                if (escalaVermelho < threshold) {
                    image.setRGB(x, y, -16777216);
                } else {
                    image.setRGB(x, y, -1);
                }

            }
        }
        return image;

    }

    private int[] getDiffXYQuadradosSuperiores(BufferedImage image) {
        // método retorna a diferença absoluta entre as coordenadas
        // X e  a diferença normal das coordenadas Y
        int coordX[] = new int[2];
        int coordY[] = new int[2];

        for (int x = 5; x < image.getWidth(); x++) {
            for (int y = 5; y < 180; y++) {

                int escalaCinzaPixel = image.getRGB(x, y) & 0xFF;

                // requisito não cumprido continue para a próxima iteranção
                if (escalaCinzaPixel > 100) {
                    continue;
                }

                // verifica o comprimento das quatro arestas para ver se é um quadrado ou um ruído
                boolean comprimento = true;
                
                // aresta horizontal superior
                for (int i = x + 1; i < x + 15; i++) {
                    escalaCinzaPixel = image.getRGB(i, y) & 0xFF;
                    if (escalaCinzaPixel > 100) {
                        comprimento = false;
                        break;
                    }
                }
                if (!comprimento) {
                    continue;
                }

                // aresta horizontal inferior
                for (int i = x + 1; i < x + 15; i++) {
                    escalaCinzaPixel = image.getRGB(i, y + 15) & 0xFF;
                    if (escalaCinzaPixel > 100) {
                        comprimento = false;
                        break;
                    }
                }
                if (!comprimento) {
                    continue;
                }
                
                // aresta vertical esquerda
                // ela se encontra após a verificação das arestas horizontais
                // pois se seu requisito não for cumprido ela quebra o loop invés 
                // de continuar para a próxima iteração
                // pois se houverem bordas irregulares, deseja-se descartar aquela
                // coordenada y, pois ela pode reconhecer valores falso deviso a sua
                // irregularidade, e o que desejamos é a primeira coordenada Y do quadrado
                for (int i = y; i < y + 15; i++) {
                    escalaCinzaPixel = image.getRGB(x, i) & 0xFF;
                    if (escalaCinzaPixel > 100) {
                        comprimento = false;
                        break;
                    }
                }
                if (!comprimento) {
                    break;
                }

                
                for (int i = y + 1; i < y + 15; i++) {
                    escalaCinzaPixel = image.getRGB(x + 15, i) & 0xFF;
                    if (escalaCinzaPixel > 100) {
                        comprimento = false;
                        break;
                    }
                }

                if (comprimento) {
                    // se o comprimento das arestas for o suficiente procurar pelo próximo quadrado                  
                    // ou quebrar os dois loops se este já for o segundo quadrado
                    if (coordY[0] == 0) {  

                        coordY[0] = y;
                        coordX[0] = x;
                        x = x + 50;
                        break;
                    } else {
                        coordY[1] = y;
                        coordX[1] = x;
                        x = image.getWidth();
                        break;
                    }
                }

            }
        }
              
        // calculando retorno
        int retorno[] = new int[2];
        retorno[0] = Math.abs(coordX[0] - coordX[1]);
        retorno[1] = coordY[0] - coordY[1];
        return retorno;

    }

        private int getXMeioQuadrado(BufferedImage image) {
        // retorna a coordenada do meio dos quadrados de referência das questões
        // como eles se encontram alinhados, a mesma servirá para todos
        // desta forma é possivel saber exatamente a coordenada para ser cortar todas
        // as questões na vertical
        // retorna -1 se nada for encontrado

        for (int x = 1; x < image.getWidth() / 8; x++) {
            for (int y = image.getHeight() / 7; y < image.getHeight() / 3; y++) {
                int escalaCinzaPixel = image.getRGB(x, y) & 0xFF;
                if (escalaCinzaPixel < 100) {
                    // pula algumas coordenadas y para evitar bordas irregulares
                    y = y + 2;
                    
                    // verifica se o pixel faz parte de uma linha horizontal de determinada largura
                    int quantidadeVizinhancaHorizontal = 0;
                    while (escalaCinzaPixel < 100) {
                        quantidadeVizinhancaHorizontal++;
                        escalaCinzaPixel = image.getRGB(x + quantidadeVizinhancaHorizontal, y) & 0xFF;
                    }
                    if (quantidadeVizinhancaHorizontal > 25) {
                        int quantidadeVizinhancaVertical = 0;
                        // verifica se o pixel faz parte de uma linha vertical de determinada largura
                        do {
                            quantidadeVizinhancaVertical++;
                            escalaCinzaPixel = image.getRGB(x, y + quantidadeVizinhancaVertical) & 0xFF;
                        } while (escalaCinzaPixel < 100);

                        if (quantidadeVizinhancaVertical > 5) {
                            // retorna o x do loop + a largura da linha / 2 que é o meio
                            return x + quantidadeVizinhancaHorizontal / 2;
                        }
                    }
                }
            }
        }
        
        // se nada for encontrado
        return -1;

    }

    private int[] getCoordsQuadrado(BufferedImage image, int x, int y) {
        // método usado para encontrar as coordenadas das 3 arestas do quadrado
        // a superior coordY1, inferior coordY2 e direita coordX
        // retorna null se houver um erro
        // aceita como argumento a coordenada da vértice superior esquerda do quadrado
        int coordX = 0;
        int coordY1 = y;
        int coordY2 = 0;

        // incremento visa tirar falhas da primeira camada do quadrado que pode
        // apresentar ruído
        y+=3;

        int vizinhancaEsquerda = 0;
        int vizinhancaDireita = 0;
        int vizinhancaInferior = 0;
        int vizinhancaInferiorDireita = 0;
        int escalaCinzaPixel = 0;

        // verifica se a coordenada faz parte de um quadrado ou se é um ruído
        // verificando se faz parte de uma linha horizontal e se possui uma altura
        // mínima
        // verificando vizinhanca esquerda
        escalaCinzaPixel = image.getRGB(x - 1, y) & 0xFF;
        while (escalaCinzaPixel < 100) {
            vizinhancaEsquerda++;
            escalaCinzaPixel = image.getRGB(x - (vizinhancaEsquerda + 1), y) & 0xFF;
        }
        // se tiver menos de 10 pixels não é um quadrado de marcação
        if (vizinhancaEsquerda < 10) {
            return null;
        }
        // verificando vizinhancaDireita
        escalaCinzaPixel = image.getRGB(x + 1, y) & 0xFF;
        while (escalaCinzaPixel < 100) {
            vizinhancaDireita++;
            // verifica a próxima posição
            escalaCinzaPixel = image.getRGB(x + (vizinhancaDireita + 1), y) & 0xFF;
        }
        // se tiver menos de 10 pixels não é um quadrado de marcação
        if (vizinhancaDireita < 10) {
            return null;
        }
        
        // verificandoVizinhancaInferior
        escalaCinzaPixel = image.getRGB(x, y + 1) & 0xFF;
        while (escalaCinzaPixel < 100) {
            vizinhancaInferior++;
            escalaCinzaPixel = image.getRGB(x, y + (vizinhancaInferior + 1)) & 0xFF;
        }
        // se tiver menos de 5 pixels não é um quadrado de marcação
        if (vizinhancaInferior < 5) {
            return null;
        }
        
       
        // verificando irregularidade da borda direita
        while (vizinhancaInferiorDireita < 10) {
            vizinhancaInferiorDireita = 0;
            // verificando se o limite da vizinhaça direita acaba em uma aresta irregular ou não
            // para retroceder uma coordenada X se sim
            escalaCinzaPixel = image.getRGB(x + vizinhancaDireita, y + 1) & 0xFF;
            while (escalaCinzaPixel < 100) {
                vizinhancaInferiorDireita++;
                escalaCinzaPixel = image.getRGB(x + vizinhancaDireita, (y + vizinhancaInferiorDireita + 1)) & 0xFF;
            }
            // se tiver menos que 10 pixels retroceda uma coordenada X
            if (vizinhancaInferiorDireita < 10) {
                vizinhancaDireita--;
            }
        }   


        // aresta direita = coordenada x original + vizinhancaDireita
        coordX = vizinhancaDireita + x;
        // aresta inferior = coordenada y original + vizinhancaInferior
        coordY2 = vizinhancaInferior + y;
        // aresta superior = coordenada y original
        return new int[]{coordY1, coordY2, coordX};

    }

    public boolean ler(String url) {
        // Lê o  gabarito contendo as questão e Ra analisadas

        BufferedImage image = readImage(url);       
        if(image == null){
            return false;
        }
        
        // alinhando a imagem
        image = alinhar(image);
        if(image == null){
            System.out.println("Não foi possível alinhar a imagem");
            return false;
        }
      
        
        // verificando a coordenada X do meio dos quadrados de marcação para
        // cornalas na vertical
        int xMeioQuadrado = getXMeioQuadrado(image);
        
        if(xMeioQuadrado == -1){
            System.out.println("Não foi possível encontrar os quadrados de referência das questões");
            return false;
        }
        
        
        int count = 0;

        // percorrendo a imagem numa linha vertical
        for (int y = image.getHeight() / 10; y < image.getHeight() - 100; y++) {
            int escalaCinzaPixel = image.getRGB(xMeioQuadrado, y) & 0xFF;
            // encontrou um ponto preto?
            if (escalaCinzaPixel < 100) {
                // É um quadrado?
                int coords[] = getCoordsQuadrado(image, xMeioQuadrado, y);
                if (coords != null) {
                    // conte o número de quadrados para referência das questões e RA
                    count++;

                    // <= 10 ainda esta lendo o RA
                    if (count <= 10) {
                        // preencha o objeto gabarito com o ra verificado e pule alguns pixels na vertical
                        // para analisar o próximo quadrado de marcação
                        setRa(count - 1, lerRa(image, coords));
                        y = coords[1] + 15;
                    } // <= 40 está lendo as questões
                    else if (count <= 40) {
                        char tempQuestoes[] = lerQuestoes(image, coords);
                        // preencha o objeto gabarito com as questões analisadas na respectiva linha
                        //  e pule alguns pixels na vertical para analisar o próximo quadrado de marcação
                        // como a linha contém 3 colunas cada uma representa uma questão 30 posições
                        // à frente da anterior ex: 1, 31, 61
                        setQuestoes(count - 11, tempQuestoes[0]);
                        setQuestoes(count + 19, tempQuestoes[1]);
                        setQuestoes(count + 49, tempQuestoes[2]);
                        y = coords[1] + 15;
                    } else {
                        // terminou a leitura quebre o loop
                        break;
                    }
                }

            }
        }
        //saveImage(image,"Gabaritos//GABARITOS04082017_teste.png","PNG"); // comando de teste
        return true;
    }

    private int lerRa(BufferedImage image, int coords[]) {
        // método responsável por ler o Ra das determinas coordenadas de quadrado
        // de marcação, retorna -1 de houver algum erro
        // Acrescentar folga às coordenadas Y para evitar erros
        int coordY1 = coords[0] - 5;
        int coordY2 = coords[1] + 5;
        int coordX = coords[2];
        int escalaCinzaPixel = 0;
        int mean = 0;
        int count = 0;
        int alternativasPreenchidas = 0;
        int retorno = -1;
        for (int i = 0; i < 10; i++) {
            mean = 0;
            count = 0;
            // pulando 40 pixels na horizontal que é a distancia entre cada quadrado
            coordX +=40;
            for (int y = coordY1; y < coordY2; y++) {
                for(int x = coordX; x < coordX + 78; x++){
                    mean += image.getRGB(x, y) & 0xFF;
                    image.setRGB(x,y,Color.red.getRGB()); // comando de testes
                    count++;
                }               
            }
            // pulando mais 64 que é o tamanho do quadrado
            coordX +=64;
            mean /= count;    
           // System.out.println("" + i + mean); // comando de testes
            // média < 200 = questão preenchida
            if (mean < 210) {
                retorno = i;
                alternativasPreenchidas++;
            }
            // preencheu mais de uma?
            if (alternativasPreenchidas > 1) {
                retorno = -1;
            }

        }

        return retorno;
    }

    private char[] lerQuestoes(BufferedImage image, int coords[]) {
        // método responsável por ler as questões da linha do respectivo quadrado
        // de marcação, retorna '0' na questão que apresentar erro
        // Acrescentar folga às coordenadas Y para evitar erros
        final char alternativa[] = {'A', 'B', 'C', 'D', 'E'};
        char retorno[] = {' ',' ',' '};
        // folga dos pixels na vertical
        int coordY1 = coords[0] - 5;
        int coordY2 = coords[1] + 5;
        int coordX = coords[2];
        int mean = 0;
        int count = 0;
        int alternativasPreenchidas = 0;
        for (int j = 0; j < 3; j++) {
            alternativasPreenchidas = 0;
            // andar 127 pixels apartir da coordX
            coordX += 127;
           // countQuestoesTeste++; // campo para teste
           // System.out.println(countQuestoesTeste); // comando de teste
            for (int i = 0; i < 5; i++) {
                mean = 0;
                count = 0;
                // andar mais 52 totalizando 179 do quadrado de marcação
                coordX += 52;
                
                // ler uma área horizontal de 79 pixels
                for (int y = coordY1; y < coordY2; y++) {
                    for (int x = coordX; x < coordX + 79; x++) {
                        mean += image.getRGB(x, y) & 0xFF;
                        image.setRGB(x,y,Color.red.getRGB()); // comando de testes
                        count++;
                    }
                }
                mean /= count;
               // System.out.println("" + alternativa[i] + mean); // comando de testes
                
                // pula esses 35 + os 52 do inicio do loop para dar 87
                // coordenada da próxima questão
                coordX += 35;
                // média < 200 = questão preenchida
                if (mean < 220) {
                    alternativasPreenchidas++;
                    retorno[j] = alternativa[i];
                }
                

            }
            // saindo do loop das alternativas pular mais 26 pixels para ajustar
            // à próxima questão
            coordX += 25;
            // preencheu mais de uma?
            if (alternativasPreenchidas > 1) {
                retorno[j] = ' ';
            }
        }

        return retorno;

    }

    private BufferedImage rotate(BufferedImage image, double angle) {
        // gira a imagem de acordo com o ângulo

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(image, null);
        
        return image;


    }

    private BufferedImage readImage(String url) {
        // lê uma imagem em arquivo
        BufferedImage image = null;

        try {

            image = ImageIO.read(new File(url));

        } catch (IOException e) {
            System.out.println(e);
        }

        return image;
    }

    private void saveImage(BufferedImage image, String url, String type) {
        // grava uma imagem em arquivo
        try {
            File file = new File(url);
            ImageIO.write(image, type, file);

        } catch (IOException e) {
            System.out.println("Não foi possível salvar a imagem");
        }
    }

}
