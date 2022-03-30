package br.senai.sp.cotia.tictactoeapp.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.tictactoeapp.R;
import br.senai.sp.cotia.tictactoeapp.databinding.FragmentJogoBinding;


public class JogoFragment extends Fragment {
    // variável para acessar os elementos da view
    private FragmentJogoBinding binding;
    // vetor de botões para referenciar os botões
    private Button[] botoes;
    // Matriz de String que representa o tabuleiro
    private String[][] tabuleiro;
    // Variáveis para os símbolos
    private String simbJog1, simbJog2, simbolo;
    // Variável random para sortear quem inicia
    private Random random;
    // Variável para controlar o número de jogadas
    private int numJogadas = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //instanciar o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        //instanciar o vetor
        botoes = new Button[9];

        // associar o vetor aos botões

        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        //associa o listener aos botões

        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }


        // Instanciar o tabuleiro
        /*tabuleiro = new String[3][3];

        // Preenche a matriz com " "
        for (int i = 0; i < 3; i++) {
            ( int j = 0;j< 3;j++){
                tabuleiro[i][j] = "";
            }
        }*/

        //instaciar o tabuleiro
        tabuleiro = new String[3][3];

        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        // Define os símbolos do Joagador 1 e Jogador 2
        simbJog1 = "X";
        simbJog2 = "O";

        // Instanciar o Random
        random = new Random();

        // Sorteia quem iniciará o jogo
        sorteia();

        // Troca a cor de acordo com qual jogador estiver jogador
        atualizaVez();

        //retorna a view root da binding
        return binding.getRoot();
    }

    private void sorteia() {
        // Se gerar um valor VERDADEIRO, jogador 1 começa
        // Caso o contrário jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simbJog1;

        } else {
            simbolo = simbJog2;
        }
    }

    private void atualizaVez() {
        if (simbolo.equals(simbJog1)) {
            binding.linearJogador1.setBackgroundColor(getResources().getColor(R.color.SpringGreen));
            binding.linearJogador2.setBackgroundColor(getResources().getColor(R.color.Crimson));

        } else {
            binding.linearJogador2.setBackgroundColor(getResources().getColor(R.color.SpringGreen));
            binding.linearJogador1.setBackgroundColor(getResources().getColor(R.color.Crimson));
        }
    }

    //Listener para os Botoes

    /*private View.OnClickListener listenerBotoes = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };*/

    private boolean venceu() {
        // Verifica se venceu nas Linhas
        for (int li = 0; li < 3; li++) {
            if (tabuleiro[li][0].equals(simbolo)
                    && tabuleiro[li][1].equals(simbolo)
                    && tabuleiro[li][2].equals(simbolo)) {
                return true;
            }
        }
        // Verifica se venceu nas Colunas
        for (int col = 0; col < 3; col++) {
            if (tabuleiro[0][col].equals(simbolo)
                    && tabuleiro[1][col].equals(simbolo)
                    && tabuleiro[2][col].equals(simbolo)) {
                return true;
            }
        }

        // Verifica se venceu na diagonal
        if (tabuleiro[0][0].equals(simbolo)
                && tabuleiro[1][1].equals(simbolo)
                && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }

        if (tabuleiro[0][2].equals(simbolo)
                && tabuleiro[1][1].equals(simbolo)
                && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }

        return false;
    }

    private void resetaGame() {
        // Percorrer os botões do Vetor, voltando o background inicial e tornando-os clicaveis novamente
        for (Button botao : botoes) {
            botao.setText("");
            botao.setBackgroundColor(getResources().getColor(R.color.teal_700));
            botao.setClickable(true);
        }
        //Limpar a Matriz
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");

        }
        // Zerar o número de jogadas
        numJogadas = 0;


    }

    private View.OnClickListener listenerBotoes = btPress -> {
        //Incrementa o número de jogadas
        numJogadas++;
        // obtem o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        // extrai a posição através do nome do botão
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);
        //extrai linha e coluna da coluna posição
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        // Preencher a posição da matriz com o simbolo "da vez"
        tabuleiro[linha][coluna] = simbolo;
        // Realiza um Casting de View para Button
        Button botao = (Button) btPress;
        // "seta" o simbolo no botão pressionado
        botao.setText(simbolo);
        // Trocar o Background do botão
        botao.setBackgroundColor(Color.TRANSPARENT);
        // Desabilitar o botão que foi pressionado
        botao.setClickable(false);
        //Verifica se venceu
        if (numJogadas >= 5 && venceu()) {
            // informa que houve um vencedor
            Toast.makeText(getContext(), R.string.vencedor, Toast.LENGTH_LONG).show();
            //Reseta
            resetaGame();
        } else if (numJogadas == 9) {
            Toast.makeText(getContext(), R.string.empate, Toast.LENGTH_LONG).show();
            //Reseta
            resetaGame();
        } else {

            //Inverte o símbolo
        /* if (simbolo.equals(simbJog1)){
            simbolo = simbJog2;
        }else{
            simbolo = simbJog1;
        }*/

            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;

            // atualiza a vez
            atualizaVez();


        }


    };


}