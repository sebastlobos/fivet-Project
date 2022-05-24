/*
 * MIT License
 *
 * Copyright (c)  2022 SEBASTIAN LOBOS ARAVENA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cl.ucn.disc.pdis.fivet.services;

import cl.ucn.disc.pdis.fivet.model.Persona;
import cl.ucn.disc.pdis.fivet.orm.DAO;
import cl.ucn.disc.pdis.fivet.orm.ORMLiteDAO;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.Optional;

public class FivetControllerImpl implements FivetController {

    private DAO<Persona> daoPersona;

    /**
     * The function of DAO.
     */
    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    public FivetControllerImpl(String dbUrl) throws SQLException {

        ConnectionSource cs = new JdbcConnectionSource(dbUrl);
        this.daoPersona = new ORMLiteDAO<>(cs, Persona.class);

    }

    @Override
    public void add(Persona persona, String password) {

    }

    @Override
    public Optional<Persona> retrieveLogin(String login) {
        return Optional.empty();
    }

    @Override
    public Optional<Persona> Autenticar(String login, String password) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer idPersona) {

    }

}
