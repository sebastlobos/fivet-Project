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
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * The control
 */
public final  class Control extends BaseEntity {
    /**
     * The Fecha
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private ZonedDateTime fecha;

    /**
     * The temperatura
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private Double temperatura;

    /**
     * The peso.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private  Double peso;

    /**
     * The Altura
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private  Double altura;

    /**
     * The diagnostico.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String diagnostico;
    /**
     * The veterinario
     */
    @Getter
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName ="veterinario_id",canBeNull = false)
    private Persona veterinario;

    /**
     * The ficha Medica
     */
    @Getter
    @DatabaseField(foreign = true, foreignAutoRefresh = true,columnName = "Fichamedica_id")
    private FichaMedica fichaMedica;
}
