import { Component, Inject, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AlertService } from '../service/alert.service';
import { HeadersComponent } from '../headers/headers.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RegisterComponent } from '../register/register.component';
import { AppointmentsComponent } from '../appointments/appointments.component';
import { UserService } from '../service/user.service';
import { User } from '../template/user';
import { DoctorInfoComponent } from '../doctor-info/doctor-info.component';


@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  currError: any;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    public dialogRef: MatDialogRef<any>,
    public dialog: MatDialog,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.currError = undefined;
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    this.userService.login({ email: this.f.email.value, password: this.f.password.value }).subscribe((user: User) => {
      if (user.email === this.f.email.value) {
        localStorage.setItem('User', JSON.stringify(user));
        this.dialogRef.close()
        if(this.route.firstChild.component=== DoctorInfoComponent){
          const dialogRef = this.dialog.open(AppointmentsComponent, {
            width: '30%',
          });
          dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
          });
        }else{
        this.router.navigate(["/user/info"]);
        }
      }
    }, error => {
      this.alertService.error(error);
      this.currError = error;
      this.loading = false;
    })
  }

  openRegisterDialog() {
    this.dialogRef.close();
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '30%',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openAppointmentsDialog() {
    this.dialogRef.close()
    console.log(this.data.doctorEmail)
    const dialogRef = this.dialog.open(AppointmentsComponent, {
      width: '50%',
      height: '30%',
      data:{doctorEmail:this.data.doctorEmail}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }


}
