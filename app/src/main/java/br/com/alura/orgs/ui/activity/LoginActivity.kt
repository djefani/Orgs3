package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.database.preferences.dataStore
import br.com.alura.orgs.database.preferences.usuarioLogadoPrefereces
import br.com.alura.orgs.databinding.ActivityLoginBinding
import br.com.alura.orgs.extensions.toast
import br.com.alura.orgs.extensions.vaiPara
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
        configuraBotaoEntrar()
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString()
            autentica(usuario, senha)
        }
    }

    private fun autentica(usuario: String, senha: String) {
        lifecycleScope.launch {
            usuarioDao.autentica(usuario, senha)?.let { usuario ->
                dataStore.edit { preferences ->
                    preferences[usuarioLogadoPrefereces] = usuario.id
                }
                vaiPara(ListaProdutosActivity::class.java)
                finish()
            } ?: toast("Falha na autenticação")
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }

}