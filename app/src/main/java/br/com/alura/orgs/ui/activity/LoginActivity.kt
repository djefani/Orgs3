package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.database.preferences.dataStore
import br.com.alura.orgs.database.preferences.usuarioLogadoPrefereces
import br.com.alura.orgs.databinding.ActivityLoginBinding
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
            Log.i("LoginActivity", "onCreate: $usuario - $senha")
            lifecycleScope.launch {
                usuarioDao.autentica(usuario, senha)?.let { usuario ->
                    dataStore.edit { preferences ->
                        preferences[usuarioLogadoPrefereces] = usuario.id
                    }
                    vaiPara(ListaProdutosActivity::class.java)
                }?:Toast.makeText(
                    this@LoginActivity,
                    "Falha na autenticação",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }

}