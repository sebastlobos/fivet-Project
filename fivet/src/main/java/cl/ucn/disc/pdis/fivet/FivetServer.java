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

package cl.ucn.disc.pdis.fivet;

import cl.ucn.disc.pdis.fivet.grpc.Credencial;
import cl.ucn.disc.pdis.fivet.grpc.FivetServiceGrpc;
import cl.ucn.disc.pdis.fivet.model.Persona;
import cl.ucn.disc.pdis.fivet.orm.ZonedDateTimeType;
import cl.ucn.disc.pdis.fivet.services.FivetController;
import cl.ucn.disc.pdis.fivet.services.FivetControllerImpl;
import com.j256.ormlite.field.DataPersisterManager;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Optional;


@Slf4j
public class FivetServer {


    @SneakyThrows
    public static void main(String[] args) {

        log.debug("Registering the ZonedDateTimeType");
        DataPersisterManager.registerDataPersisters(ZonedDateTimeType.INSTANCE);

        log.debug(("Building the FivetServiceImpl"));
        FivetServiceImpl fivetService = new FivetServiceImpl("jdbc:h2:mem:fivet");

        Server server = ServerBuilder.forPort(5000)
                .addService(fivetService)
                .build()
                .start();
        server.awaitTermination();
    }

    @Slf4j
    private static class FivetServiceImpl extends FivetServiceGrpc.FivetServiceImplBase {

        /**
         * The Controller
         */
        private final FivetController fivetController;

        /**
         * @param databaseUrl
         * @throws SQLException
         */
        public FivetServiceImpl(String databaseUrl) {
            this.fivetController = new FivetControllerImpl(databaseUrl);
        }

        /**
         * Authenticate
         *
         * @param request          credentials of Persona
         * @param responseObserver StreamObserver of Persona
         */
        @Override
        public void autenticar(Credencial request, StreamObserver<Persona> responseObserver) {

            // Retrieve from Controller
            Optional<cl.ucn.disc.pdis.fivet.model.Persona> persona2 = this.fivetController.retrieveByLogin(
                    request.getLogin());
            if (persona2.isPresent()) {
                // Return the observer
                responseObserver.onNext(Persona.newBuilder()
                        .setRut(persona2.get().getRut())
                        .setNombre(persona2.get().getNombre())
                        .setEmail(persona2.get().getEmail())
                        .setDireccion(persona2.get().getDireccion())
                        .build());
                responseObserver.onCompleted();
            } else {
                responseObserver.onNext(Persona.newBuilder()
                        .setRut("12312445566")
                        .setNombre("a")
                        .setEmail("a@alumnos.ucn.cl")
                        .setDireccion("Angamos #0610")
                        .build());
                responseObserver.onCompleted();
            }
        }
    }
}

