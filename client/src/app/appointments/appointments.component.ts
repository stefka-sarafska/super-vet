import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BookAppointmentComponent } from '../book-appointment/book-appointment.component';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
import { AppointmentService } from '../service/appointment.service';
import { Appointment } from '../template/appointment';
import { DoctorTemplate } from '../template/doctor';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {

  doctorEmail:string;
  appointments: Appointment[];


  constructor(public dialogRef: MatDialogRef<RegisterComponent | LoginComponent>, private appointmentsService: AppointmentService,
    public dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: any) {
      this.doctorEmail  = JSON.parse(localStorage.getItem('DoctorEmail'));
  }

  ngOnInit(): void {
    this.getDoctorAppointments()
  }

  convertDate(date: Date) {
    return this.appointmentsService.convertDate(date);
  }

  getDoctorAppointments() {
    this.appointmentsService.getDoctorFreeAppointments(this.doctorEmail).subscribe((appointments: Appointment[]) => {
      this.appointments = appointments;
    })
  }



  openBookAppointmentsDialog(appointment: Appointment) {
    console.log(appointment)
    this.dialogRef.close()
    const dialogRef = this.dialog.open(BookAppointmentComponent, {
      width: '30%',
      data: appointment
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
