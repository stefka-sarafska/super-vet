import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Form } from '@angular/forms';
import { DoctorTemplate } from '../template/doctor';
import { DoctorRequest } from '../template/doctor-request';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {


  constructor(private http: HttpClient) { }

  uploadImage(doctorEmail:string,uploadImageData:FormData){
    this.http.post('/api/'+doctorEmail+'/upload', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          console.log('Image uploaded successfully');
        } else {
          console.log('Image not uploaded successfully'); 
        }
      }
      );
  }

  getDoctorImage(doctorImage){
    
  }
  sendDoctorRequest(doctor: any) {
    return this.http.post<DoctorTemplate>("api/doctor/request", doctor);
  }

  getAllDoctors() {
    return this.http.get<any>("/api/all/doctors");
  }

  getAllDoctorsSortedByAddress() {
    return this.http.get<any>("/api/all/doctors/sortedByAddress");
  }

  getAllDoctorsSortedBySpecialty() {
    return this.http.get<any>("/api/all/doctors/sortedBySpecialty");
  }

  getAllDoctorsByAddress(address: string) {
    return this.http.get<any>("/api/doctors/" + address);
  }

  getAllDoctorsBySpecialty(specialty: string) {
    return this.http.get<any>("/api/doctors/specialty/" + specialty);
  }

  //trqbwa da getvam po imejl ne po imena tyk kato moje da ima doktori s ednakwi imena
  getDoctorByName(name: string) {
    return this.http.get<any>("/api/doctors/name/" + name);
  }

  getAllDoctorsByAddressAndSpecialty(specialty: string, address: string) {
    return this.http.get<any>("/api/doctors/specialty/" + specialty + "/address/" + address);
  }

  getDoctorRequests() {
    return this.http.get<any>("/api/doctor/requests");
  }

  addDoctorRequest(doctor: DoctorRequest) {
    return this.http.post<any>("/api/doctor/request", doctor);
  }

  addDoctor(doctor: DoctorRequest) {
    return this.http.post<any>("/api/add/doctor", doctor);
  }

  deleteDoctor(doctor: DoctorRequest) {
    return this.http.post<any>("/api/delete/doctor", doctor);
  }

  getDoctorByEmail(email: string) {
    return this.http.get<any>("/api/doctors/email/" + email);
  }

  getDoctorsByAddressAndName(address: string, name: string) {
    return this.http.get<any>("  /doctors/address/" + address + "/name/" + name);
  }

  getDoctorsBySpecialtyAndName(specialty: string, name: string) {
    return this.http.get<any>("/doctors/specialty/" + specialty + "/name/" + name);
  }

  getDoctorsBySpecialtyAddressAndName(specialty: string, address: string, name: string) {
    return this.http.get<any>("/doctors/specialty/" + specialty + "/address/" + address + "/name/" + name);
  }




}