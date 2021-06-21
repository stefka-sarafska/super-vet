import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { AppointmentsComponent } from '../appointments/appointments.component';
import { LoginComponent } from '../login/login.component';
import { AppointmentService } from '../service/appointment.service';
import { DoctorService } from '../service/doctor.service';
import { Appointment } from '../template/appointment';
import { DoctorTemplate } from '../template/doctor';

@Component({
  selector: 'app-doctor-info',
  templateUrl: './doctor-info.component.html',
  styleUrls: ['./doctor-info.component.css']
})
export class DoctorInfoComponent implements OnInit {

  doctor: any
  earlierAppointment: Appointment;
  firstName: string;
  middleName: string;
  lastName: string

  doctorEmail:any;

  constructor(private router:Router,private routeActiveted: ActivatedRoute,
     private doctorService: DoctorService, private appointmentService: AppointmentService,
      public dialog: MatDialog) {
    if (this.router.getCurrentNavigation().extras.state === undefined) {
      console.log(1)
      this.doctorEmail  = JSON.parse(localStorage.getItem('DoctorEmail'));
      console.log(JSON.parse(localStorage.getItem('DoctorEmail')))
    } else {
      console.log(2)
      this.doctorEmail  = this.router.getCurrentNavigation().extras.state.email
      console.log( this.router.getCurrentNavigation().extras.state)
      localStorage.setItem('DoctorEmail', JSON.stringify(this.doctorEmail));
      console.log(JSON.parse(localStorage.getItem('DoctorEmail')))
    }
   
  }


  ngOnInit(): void {
    this.routeActiveted.params.subscribe(params => {

      this.firstName = params['firstName'];
      this.middleName = params['middleName'];
      this.lastName = params['lastName'];
    });
    this.getDoctorByEmail(this.doctorEmail).then((data:DoctorTemplate)=>{
      this.getEarlierAppointment(data.email)
    })
   
  }


  getDoctorByEmail(email:string) {
    return new Promise((resolve,reject)=>{
      this.doctorService.getDoctorByEmail(email).subscribe((doctor: DoctorTemplate) => {
        this.doctor = doctor;
        console.log(doctor)
        resolve(doctor)
      })
    })
   
  }

  convertDate(date: Date) {
    return this.appointmentService.convertDate(date);
  }

  getEarlierAppointment(doctorEmail:string) {
    this.appointmentService.getDoctorEarlierAppointment(doctorEmail).subscribe((appointment: Appointment) => {
      this.earlierAppointment = appointment;
    })

  }

  openLoginDialog() {
    if (localStorage.getItem('User')) {
      const dialogRef = this.dialog.open(AppointmentsComponent, {
        width: '50%',
        height: '30%',
        data:{doctorEmail:this.doctor.email}
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    } else {
      const dialogRef = this.dialog.open(LoginComponent, {
        width: '40%',
        data:{doctorEmail:this.doctor.email}
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }
  }




}
function email(email: any) {
  throw new Error('Function not implemented.');
}

