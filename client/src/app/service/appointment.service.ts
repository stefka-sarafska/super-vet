import { DatePipe } from "@angular/common";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BookAppointment } from "../template/book-appointmet";

@Injectable({
    providedIn: 'root',
})
export class AppointmentService {

    constructor(private http: HttpClient, public datepipe: DatePipe) { }

    getDoctorEarlierAppointment(doctorEmail: string) {
        return this.http.get<any>("/api/doctors/" + doctorEmail + "/earlier/appointment")
    }

    getDoctorBookedAppointments(doctorEmail: string) {
        return this.http.get<any>("/api/doctors/" + doctorEmail + "/booked/appointments")
    }

    getDoctorFreeAppointments(doctorEmail: string) {
        return this.http.get<any>("/api/doctors/" + doctorEmail + "/free/appointments")
    }

    getAppointmentReasons() {
        return this.http.get<any>("/api/appointment/reasons")
    }

    bookAppointment(bookAppointment: BookAppointment) {
        return this.http.post("/api/book/appointment", bookAppointment)
    }

    getPatientAppointments(email:string){
         return this.http.get<any>("/api/patient/" + email + "/appointments")
    }

    convertDate(date: Date) {
        return this.datepipe.transform(date, 'dd-MM-yyyy HH:mm')
    }

    getDateTime(date:Date){
        return this.datepipe.transform(date, 'HH:mm')
    }

    getDate(date:Date){
        return this.datepipe.transform(date, 'dd-MM-yyyy')
    }

   

}