package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("""SELECT * FROM Usuario 
        WHERE id = :usuarioId 
        AND senha = :senha""")
    fun autentica(usuarioId: String, senha: String) : Usuario?
}