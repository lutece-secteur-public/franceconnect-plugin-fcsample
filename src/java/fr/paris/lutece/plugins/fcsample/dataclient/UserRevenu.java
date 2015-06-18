/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

/*
Sample from dgfip (mockup from opendatasoft)
{
   "parameters" : {
      "rows" : 10,
      "dataset" : [
         "dgfip_rfr"
      ],
      "format" : "json",
      "timezone" : "UTC"
   },
   "nhits" : 1,
   "records" : [
      {
         "fields" : {
            "pays_de_naissance" : "99100",
            "nom_d_usage" : "PETIT",
            "prenom_de_naissance_enfants" : "PIERRE, LUCIEN",
            "date_de_naissance" : "1985-01-05",
            "nom_de_naissance_du_conjoint" : "PETIT",
            "sexe" : "female",
            "prenoms" : "Patricia",
            "nom_de_naissance" : "AMA",
            "rfr" : 54000,
            "hypothese_dgfip" : "Couple marié avec PETIT, 2 enfants (né en 2010 et 2013), revenu 30K€+30K€, nés en 1983 et 85, vivent en métropole",
            "reference_de_l_avis" : "15 78A001138 37",
            "p237" : "https://doc.integ01.dev-franceconnect.fr/tmp/P237.pdf",
            "avisn_1" : "https://doc.integ01.dev-franceconnect.fr/tmp/Petit 2015.pdf",
            "prenom_de_naissance_du_conjoint" : "Francois -Pierre",
            "nombre_de_parts" : 3,
            "lieu_de_naissance" : "91471",
            "avisn_2" : "https://doc.integ01.dev-franceconnect.fr/tmp/Petit 2014.pdf",
            "situation_foyer" : "M",
            "avisn_3" : "https://doc.integ01.dev-franceconnect.fr/tmp/Petit 2013.pdf"
         },
         "recordid" : "2f567b361eaafb712cfdb1c5bc28e099c2f32310",
         "datasetid" : "dgfip_rfr",
         "record_timestamp" : "2015-06-17T15:46:07+00:00"
      }
   ]
}
 *
 */

package fr.paris.lutece.plugins.fcsample.dataclient;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * UserRevenu
 */
public class UserRevenu implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Integer nRFR;

    /**
     * Sets the Records
     * @param records The Records
     */
    @JsonProperty( "records" )
    public void setRecords( Object[] records )
    {
        //"rfr" : 54000,
        //"nombre_de_parts" : 3,
        //
        Map<String, Object> record = ( Map<String,Object> ) records[0];
        Map<String, Object> fields = ( Map<String,Object> ) record.get( "fields" );
        Integer RFR                = ( Integer )            fields.get( "rfr" );
        nRFR = RFR;
    }

    /**
     * Returns the RFR
     * @return The RFR
     */
    public Integer getRFR(  )
    {
        return nRFR;
    }
}
