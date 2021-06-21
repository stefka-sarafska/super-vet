import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AppointmentService } from '../service/appointment.service';
import { DoctorService } from '../service/doctor.service';
import { Appointment } from '../template/appointment';
import { AppointmentInfo } from '../template/appointment-info';
import { DoctorAppointmentInfo } from '../template/doctor-appointment-info';
import { DoctorRequest } from '../template/doctor-request';
import { User } from '../template/user';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  loggedUser: User;
  doctorRequests: [];
  displayedColumns: string[];
  dataSource = [];

  constructor(private appointmentService: AppointmentService, private doctorService: DoctorService) {
    this.loggedUser = JSON.parse(localStorage.getItem('User'))
  }

  ngOnInit(): void {

    this.getResources()
  }

  getResources() {
    if (this.loggedUser.role === "ADMIN") {
      this.displayedColumns = ['name', 'email', 'address', 'phone', 'specialty', 'description', 'biography', 'qualifications', 'formation', 'action'];
      this.getDoctorRequests()
    } else if (this.loggedUser.role === "USER") {
      this.displayedColumns = ['date', 'time', 'reason', 'address', 'doctor', 'phone'];
      this.getPatientAppointmentsInfo();
    } else if (this.loggedUser.role === "DOCTOR") {
      this.displayedColumns = ['date', 'time', 'reason', 'patient', 'phone','email'];
      this.getDoctorAppointmentsInfo();
    }
  }

  getDoctorRequests() {
    this.doctorService.getDoctorRequests().subscribe((requests: DoctorRequest[]) => {
      requests.forEach((request: DoctorRequest) => {
        this.dataSource.push({
          name: request.firstName + " " + request.middleName + " " + request.lastName, email: request.email, address: request.address, phone: request.phoneNumber,
          specialty: request.specialty, description: request.description, biography: request.biography, qualifications: request.qualifications, formation: request.formation
        })

      })
    })
  }

  getPatientAppointmentsInfo() {
    this.appointmentService.getPatientAppointments(this.loggedUser.email).subscribe((appointments: AppointmentInfo[]) => {
      appointments.forEach((appointment: AppointmentInfo) => {
        this.dataSource.push({
          date: this.appointmentService.getDate(appointment.date), time: this.appointmentService.getDateTime(appointment.date), reason: appointment.reason, address: appointment.doctorAddress,
          doctor: appointment.doctorFirstName + " " + appointment.doctorLastName, phone: appointment.doctorPhone
        })
      })

    })
  }

  getDoctorAppointmentsInfo() {
    this.appointmentService.getDoctorBookedAppointments(this.loggedUser.email).subscribe((appointments: DoctorAppointmentInfo[]) => {
      console.log(appointments)
      appointments.forEach((appointment: DoctorAppointmentInfo) => {
        this.dataSource.push({
          date: this.appointmentService.getDate(appointment.date), time: this.appointmentService.getDateTime(appointment.date),
          reason: appointment.reason, patient: appointment.patientFirstName + " " + appointment.patientLastName,
          phone: appointment.patientPhone,
          email: appointment.patientEmail
        })
      })

    })
  }

  addRequest(element: any) {
    let names = element.name.split(" ");
    let firstName = names[0];
    let middleName = names[1];
    let lastName = names[2];
    let doctorRequest = {
      firstName: firstName, middleName: middleName, lastName: lastName, email: element.email, phoneNumber: element.phoneNumber,
      specialty: element.specialty, address: element.address, description: element.description, formation: element.formation, qualifications: element.qualifications, biography: element.biography
    }
    this.doctorService.addDoctor(doctorRequest).subscribe(() => {
      console.log("Doctor added!")
    })
    console.log(element)
  }

  deleteRequest(element: any) {
    let names = element.name.split(" ");
    let firstName = names[0];
    let middleName = names[1];
    let lastName = names[2];
    let doctorRequest = {
      firstName: firstName, middleName: middleName, lastName: lastName, email: element.email, phoneNumber: element.phoneNumber,
      specialty: element.specialty, address: element.address, description: element.description, formation: element.formation, qualifications: element.qualifications, biography: element.biography
    }
    this.doctorService.deleteDoctor(doctorRequest).subscribe(() => {
      console.log("Doctor deleted!")
    })
    console.log(element)
  }

}
