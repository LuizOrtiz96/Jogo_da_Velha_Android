package br.senai.sp.cotia.tictactoeapp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.senai.sp.cotia.tictactoeapp.R;
import br.senai.sp.cotia.tictactoeapp.databinding.FragmentInicioBinding;
import br.senai.sp.cotia.tictactoeapp.databinding.FragmentJogoBinding;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        binding.btInicio.setOnClickListener(v -> {
            NavHostFragment.findNavController(InicioFragment.this).
                    navigate(R.id.action_inicioFragment_to_jogoFragment);

        });
        return binding.getRoot();




    }

    @Override
    public void onStart() {
        super.onStart();
        // PEga a referência para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        // Oculta a action bar
        minhaActivity.getSupportActionBar().hide();

    }

}