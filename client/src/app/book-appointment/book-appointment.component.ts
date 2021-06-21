import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AppointmentsComponent } from '../appointments/appointments.component';
import { AppointmentService } from '../service/appointment.service';
import { Appointment } from '../template/appointment';

@Component({
  selector: 'app-book-appointment',
  templateUrl: './book-appointment.component.html',
  styleUrls: ['./book-appointment.component.css']
})
export class BookAppointmentComponent implements OnInit {

  selectedReason:any;
  appointmentReasons: [];
  appointmentDate: any;
  user:any;

  constructor(public dialogRef: MatDialogRef<AppointmentsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Appointment,
     private appointmentService: AppointmentService,
     private router:Router) { 
    this.appointmentDate=appointmentService.convertDate(data.date)
    this.user = JSON.parse(localStorage.getItem('User'));
  }

  ngOnInit(): void {
    this.getAppointmentReasons()
  }

  bookAppointment(){
    let bookAppointmet={doctorEmail:this.data.doctorEmail,date:this.data.date,reason:this.selectedReason,userEmail:this.user.email,firstName:this.user.firstName,middleName:this.user.middleName,lastName:this.user.lastName,phoneNumber:this.user.phoneNumber}
    this.appointmentService.bookAppointment(bookAppointmet).subscribe((data:any)=>{
      console.log("Appointment booked!")
      this.router.navigate(["/user/info"]);
      this.dialogRef.close()
    })
  }

  getAppointmentReasons() {
    this.appointmentService.getAppointmentReasons().subscribe((reasons: []) => {
      this.appointmentReasons = reasons;
    })
  }

}
