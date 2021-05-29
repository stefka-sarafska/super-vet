import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DoctorTemplate } from '../template/doctor';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {

    constructor(private http: HttpClient) { }

    sendDoctorRequest(doctor:DoctorTemplate){
        console.log(doctor)
        return this.http.post<DoctorTemplate>("/api/email", doctor);
    }

}