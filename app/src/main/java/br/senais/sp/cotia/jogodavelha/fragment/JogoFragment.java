package br.senais.sp.cotia.jogodavelha.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Random;

import br.senais.sp.cotia.jogodavelha.R;
import br.senais.sp.cotia.jogodavelha.databinding.FragmentJogoBinding;
import br.senais.sp.cotia.jogodavelha.util.PrefUtil;


public class JogoFragment extends Fragment {
    //variavel para acessar os elementos da View
    private FragmentJogoBinding binding;
    // valor de botôes para a referencia os botões
    private Button[] botoes;
    //matriz de String que representa o tabuleiro
    private String[][] tabuleiro;
    //variavel para os simbolos
    private String simJog1, simJog2, simbolo, jog1nome, jog2nome;
    //variavel para sortear quem inicia
    private Random random;
    //variavel para controlar o numero de jogadas
    private int numJogadas = 0;
    //variavel para o placar
    private int placarJog1 = 0, placarJog2 = 0, velhas = 0;
    private AlertDialog alerta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //habilitar o menu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        //instaciar o vetor
        botoes = new Button[9];

        //Assosciar os botoes ao vetor
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }

        //instanciar o tabuleiro
        tabuleiro = new String[3][3];

        //prencher a matriz com ""
        for (int l = 0; l < 3; l++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[l][j] = "";
            }
        }

        //iniciando um new randon
        random = new Random();

        //Define os simbolos do jogador 1 e jogador 2
        simJog1 = PrefUtil.getSimboloJog1(getContext());
        simJog2 = PrefUtil.getSimboloJog2(getContext());
        jog1nome = PrefUtil.getNomeJog1(getContext());
        jog2nome = PrefUtil.getNomeJog2(getContext());

        //atualizar o placar com os simbolos
        binding.tvJog1.setText(getResources().getString(R.string.jog1, jog1nome, simJog1));
        binding.tvJog2.setText(getResources().getString(R.string.jog2, jog2nome, simJog2));



        //sorteia que iniciará o jogo
        soteia();

        //Atualiza a cor do placar de quem for jogar
        atualizaVez();



        //retorna a view root da binding
        return binding.getRoot();
    }

    private void soteia() {
        //gera um valor VERDADEIRO, jogador 1 começa
        //caso contrario jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simJog1;
        } else {
            simbolo = simJog2;
        }
    }

    private void atualizaVez() {
        if (simbolo.equals(simJog1)) {
            binding.linearJog1.setBackgroundColor(getResources().getColor(R.color.roxo_escuro));
            binding.linearJog2.setBackgroundColor(getResources().getColor(R.color.roxo));
        } else {
            binding.linearJog2.setBackgroundColor(getResources().getColor(R.color.roxo_escuro));
            binding.linearJog1.setBackgroundColor(getResources().getColor(R.color.roxo));
        }
    }

    private boolean venceu() {
        //verifica se venceu nas linha
        for (int li = 0; li < 3; li++){
            if (tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)){
                return true;
            }
        }

        for (int col = 0; col < 3; col++){
            if (tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)){
                return true;
            }
        }

        // verifica se venceu nas diagonais

        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)){
            return true;
        }

        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)){
            return true;
        }

        return false;

    }

    private void atualizaPlacar(){
        binding.placarJog1.setText(placarJog1+"");
        binding.placarJog2.setText(placarJog2+"");
    }

    private void resetar(){
        for (Button bt : botoes) {
           bt.setBackgroundColor(getResources().getColor(R.color.roxo));
           bt.setText("");
           bt.setClickable(true);
           soteia();
           atualizaVez();
        }
        //limpar a matriz
        for (int l = 0; l < 3; l++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[l][j] = "";
            }
        }

        //limpar o numero de jogadas
        numJogadas = 0;
        placarJog1 = 0;
        placarJog2 = 0;
        velhas = 0;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_resetar:
                dialog();

                break;
                //caso seja a opção de preferencia
            case R.id.menu_pref:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;
            case R.id.menu_inicio:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_inicioFragment);
                break;

        }
        return true;
    }

    private View.OnClickListener listenerBotoes = btPress -> {


        numJogadas++;
        //obter o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai a posicao atráves do nome do botao
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);
        //extrai a linha e coluna da string posição
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

        //preencher a posição da matriz com o "simbolo da vez"
        tabuleiro[linha][coluna] = simbolo;
        // faz um casting de view para button
        Button botao = (Button) btPress;
        // "seta" o símbolo no botão pressionado
        botao.setText(simbolo);
        botao.setBackgroundColor(Color.WHITE);
        //desabilitar o botão que foi pressionado
        botao.setClickable(false);
        //verifica se venceu
        if (numJogadas >= 5 && venceu()) {
            //informa que houve um vencedor
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();

            if (simbolo.equals(simJog1)) {
                placarJog1++;
            }else {
                placarJog2++;
            }
            atualizaPlacar();
            resetar();

        }else if(numJogadas == 9){
            velhas++;
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
            binding.totalvelhas.setText(getResources().getString(R.string.total_velhas, velhas));
            resetar();
        } else {
            //inverte o simbolo
            simbolo = simbolo.equals(simJog1) ? simJog2 : simJog1;

            //atualizar a vez
            atualizaVez();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        //pega a referência para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().show();
        //desabilita a seta de retornar
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void dialog() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //define o titulo
        builder.setTitle("Revanche?");
        //define a mensagem
        builder.setMessage("Deseja reiniciar");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                velhas = 0;
                atualizaPlacar();
                resetar();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
