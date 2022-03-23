package br.senais.sp.cotia.jogodavelha.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.senais.sp.cotia.jogodavelha.R;
import br.senais.sp.cotia.jogodavelha.databinding.FragmentJogoBinding;


public class JogoFragment extends Fragment {
    //variavel para acessar os elementos da View
    private FragmentJogoBinding binding;
    // valor de botôes para a referencia os botões
    private Button[] botoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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

        for (Button bt : botoes){
            bt.setOnClickListener(listenerBotoes);
        }

       //retorna a view root da binding


        return binding.getRoot();
    }

    private View.OnClickListener listenerBotoes = btPress -> {
       //obter o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai a posicao atráves do nome do botao
        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        //extrai a linha e coluna da string posição
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

        Log.w("BOTAO", linha + "");
        Log.w("BOTAO", coluna + "");

    };

}
