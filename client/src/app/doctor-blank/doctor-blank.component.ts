import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { DoctorService } from '../service/doctor.service';
import { DoctorTemplate } from '../template/doctor';


@Component({
  selector: 'app-doctor-blank',
  templateUrl: './doctor-blank.component.html',
  styleUrls: ['./doctor-blank.component.css']
})
export class DoctorBlankComponent implements OnInit {

  phone = new FormControl('', Validators.pattern('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$'));
  email = new FormControl('', [Validators.required, Validators.email]);

  constructor(private doctorService: DoctorService) { }

  ngOnInit(): void {
  }

  onSubmit(firstName: string, middleName: string, lastName: string, address: string, specialty: string, phone: string) {
    let doctor = { firstName: firstName, middleName: middleName, lastName: lastName, address: address, specialty: specialty, phoneNumber: phone, email: this.email.value };
    this.doctorService.sendDoctorRequest(doctor).subscribe(() => {})
  }


  getPhoneErrorMessage(){
    if (this.phone.hasError('required')) {
      return 'You must enter a phone';
    }
    return this.phone.hasError('phone') ? 'Not a valid phone' : '';
  }

  getEmailErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

}
