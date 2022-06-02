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

package cl.ucn.disc.pdis.fivet.model;

import cl.ucn.disc.pdis.fivet.orm.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

/**
 * ficha medica class.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable
public final class FichaMedica extends BaseEntity {

    /**
     * The number.
     */
    @Getter
    @DatabaseField(canBeNull = true, unique=true)
    private Integer numero;

    /**
     * The nombre Of paciente
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String nombrePaciente;
    /**
    * The especie.
    */
    @Getter
    @DatabaseField(canBeNull = false)
    private  String especie;
    /**
     * The fecha nacimiento.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private LocalDate FechaNacimiento;

    /**
     * The raza.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String raza;

    /**
     * The Color.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String color;
    /**
     * The tipo.
     */

    @Getter
    @DatabaseField(canBeNull = false)
    private String tipo;

    /**
     * The Sexo.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private Sexo sexo;

    /**
     * The Duenio.
     */
    @Getter
    @DatabaseField(canBeNull = true, foreignAutoRefresh = true, columnName = "persona_id")
    private Persona duenio;

    /**
     * The controles.
     */
    @Getter
    @ForeignCollectionField(eager = true, orderColumnName = "fecha" )
    private Collection<Control> controles;

    /**
     * Append a control
     */
    public void add(Control control){
        this.controles.add(control);
    }
    /**
     * The Sexo.
     */
    public enum Sexo{
        MACHO,
        HEMBRA
    }
}
