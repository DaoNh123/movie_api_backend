package com.example.backend_sem2.model.rapidApi;

import com.example.backend_sem2.Enum.MovieLabelEnum;
import com.example.backend_sem2.entity.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Certificate {
    private String certificate;         // convert to movie label
    private String certificateNumber;
    private String ratingReason;
    private String ratingsBody;
    private String country;

    /*  certificate for Movie in USA before convert to VietName standard
    * G - For all audiences
    * PG - Parental Guidance Suggested (mainly for under 10's)
    * PG-13 - Parental Guidance Suggested for children under 13
    * R - Under 17 not admitted without parent or guardian
    * NC-17 - Under 17 not admitted
    * Approved - Pre-1968 titles only (from the MPA site) Under the Hays Code,
    * films were simply approved or disapproved based on whether they were deemed 'moral' or 'immoral'.)*/

    /*  Convert rule in this project
    *   USA         ==>     VietNam Movie Label
    *   G           ==>     P
    *   PG, PG-13   ==>     C12
    *   R           ==>     C16
    *   Other       ==>     C18
    * */

    public MovieLabelEnum getMovieLabelEnum(){
        if(certificate.equals("G")) return MovieLabelEnum.P;
        else if(certificate.equals("PG") || certificate.equals("PG-13")) return MovieLabelEnum.C12;
        else if(certificate.equals("R")) return MovieLabelEnum.C16;
        else return MovieLabelEnum.C18;
    }
}
