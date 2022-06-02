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

import cl.ucn.disc.pdis.fivet.model.FichaMedica;
import cl.ucn.disc.pdis.fivet.model.Persona;
import cl.ucn.disc.pdis.fivet.orm.DAO;
import cl.ucn.disc.pdis.fivet.orm.ORMLiteDAO;
import com.j256.ormlite.support.ConnectionSource;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ToStringBuilder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.naming.ldap.Control;
import java.time.LocalDate;
import java.time.ZonedDateTime;



/**
 * The test of model
 */
@Slf4j
public  final class TestModel {
/**
 * Testing the model
 */
    @SneakyThrows
    @Test
    public void testModel(){
        log.info("Starting the Test..");
        //The Password encoder
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        log.debug("Build the Conecttion Source..");

        //The connection
        @Cleanup
        ConnectionSource cs = ORMLiteDAO.buildConnectionSource("jdhc:h2:mem:fivet");

        log.debug("Loading Ficha Medica...");
        DAO< FichaMedica> daoFichaMedica = new ORMLiteDAO<>(cs, FichaMedica.class);
        daoFichaMedica.dropAndCreateTable();

        log.debug("Loading Person ..");
        DAO< Persona> daoPersona = new ORMLiteDAO<>(cs , Persona.class);
        daoPersona.dropAndCreateTable();

        log.debug("Loading Persona ..");
        DAO<Persona> daoControl = new ORMLiteDAO<>(cs, Control.class);
        daoControl.dropAndCreateTable();

        // Saving a Persona
        {
            Persona persona =Persona.builder()
                    .rut("130144918")
                    .nombre("Diego Urrutia-Astorga")
                    .password(passwordEncoder.encode("durrutia123"))
                    .direccion("Angamos #0618")
                    .build();
            daoPersona.save(persona);
            print("Persona", persona);
            Assertions.assertNotNull(persona.getId(), "ID WAS null");
        }
        //retrieve a Persona
        {
            Persona persona = daoPersona.get(1).orElseThrow();
            print("Persona", persona);
            // Save a Persona
            {
                persona = daoPersona.get(1).orElseThrow();
                print("persona", persona);

            }
            //save a FichaMedica
            {
                FichaMedica fichaMedica = FichaMedica.builder()
                        .numero(100)
                        .nombrePaciente("Rusia")
                        .especie("Felino")
                        .FechaNacimiento(LocalDate.now())
                        .raza("tabby")
                        .color("Amarilla")
                        .tipo("Gigante")
                        .duenio(persona)
                        .sexo(FichaMedica.Sexo.MACHO)
                        .build();
                        daoFichaMedica.save(fichaMedica);
                        Assertions.assertNotNull(fichaMedica.getId()," ID was null");
                        print("FichaMedica", fichaMedica);

            }

        }

        // Retrieve a FichaMedica
        {
            FichaMedica fichaMedica = daoFichaMedica.get(1).orElseThrow();
            print("FichaMedica", fichaMedica);
            print("Duenio", fichaMedica.getDuenio());
            Assertions.assertNotNull(fichaMedica.getDuenio(), "Duenio was null");
            // Create a Control
            {
                cl.ucn.disc.pdis.fivet.model.Control control = control.builder()
                        .altura(0.6)
                        .diagnostico("Sin novedad")
                        .fecha(ZonedDateTime.now())
                        .peso(10.5)
                        .temperatura(39.9)
                        //.fichaMedica(fichaMedico)
                        .FichaMedica(fichaMedica.getDuenio())
                        .veterinario(fichaMedica.getDuenio())
                        .build();
                fichaMedica.add(control);
            }
        }
            //Retrieve a FichaMedica with control
        {
            FichaMedica fichaMedica =daoFichaMedica.get(1).orElseThrow();
            print("FichaMedica", fichaMedica);
            Assertions.assertNotNull(fichaMedica.getControles(),"Controles was null");
            Assertions.assertEquals(1, fichaMedica.getControles().size(), " Controles! =!3");
            for(cl.ucn.disc.pdis.fivet.model.Control c : fichaMedica.getControles()){
                print("control", c);
            }
        }
        log.info("Done");
    }
    /**
     * Print a Object with message
     */
    private  static  void print (String message, Object o){
        log.debug("{}:{}", message, ToStringBuilder.reflectionToString(o, ToStringStyle.MULTI_LINE_STYLE));
    }
}
