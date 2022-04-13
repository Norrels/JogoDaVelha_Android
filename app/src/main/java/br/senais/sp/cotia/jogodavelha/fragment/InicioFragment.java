package br.senais.sp.cotia.jogodavelha.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.senais.sp.cotia.jogodavelha.R;
import br.senais.sp.cotia.jogodavelha.databinding.FragmentInicioBinding;
import br.senais.sp.cotia.jogodavelha.util.PrefUtil;


public class InicioFragment extends Fragment {

  private String nomejog1, nomejog2;
  private FragmentInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container,  false);

        binding.btJogar.setOnClickListener(v ->{
            nomejog1 = binding.etNomejog1.getText().toString();
            nomejog2 = binding.etNomejog2.getText().toString();

            if (!nomejog1.equals("")){
                PrefUtil.setNomeJog1(nomejog1, getContext());
            } else {
                PrefUtil.setNomeJog1("Jogador", getContext());
            }
           if (!nomejog2.equals("")){
               PrefUtil.setNomeJog2(nomejog2, getContext());
           } else {
               PrefUtil.setNomeJog2("Jogador", getContext());
           }


            NavHostFragment.findNavController(InicioFragment.this).navigate(R.id.action_inicioFragment_to_jogoFragment);
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        //pega a referência para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().hide();
    }
}

