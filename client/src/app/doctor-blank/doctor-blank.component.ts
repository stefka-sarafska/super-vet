import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DoctorService } from '../service/doctor.service';


@Component({
  selector: 'app-doctor-blank',
  templateUrl: './doctor-blank.component.html',
  styleUrls: ['./doctor-blank.component.css']
})
export class DoctorBlankComponent implements OnInit {

  phone = new FormControl('', Validators.pattern('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$'));
  email = new FormControl('', [Validators.required, Validators.email]);
  currError: any;

  constructor(private doctorService: DoctorService, private route: Router) { }

  ngOnInit(): void {
  }

  onSubmit(firstName: string, middleName: string, lastName: string, password: string, confirmPassword: string, specialty: string, address: string, description: string, formation: string, qualifications: string, biography: string) {
    let doctor = {
      firstName: firstName, middleName: middleName, lastName: lastName, email: this.email.value, password: password, repeatPassword: confirmPassword,
      phoneNumber: this.phone.value, specialty: specialty, address: address, description: description, formation: formation, qualifications: qualifications, biography: biography,
    };
    this.doctorService.sendDoctorRequest(doctor).subscribe(() => {
      this.doctorService.uploadImage(doctor.email, this.uploadImageData);
      this.route.navigateByUrl("/")
    }, error => {
      this.currError = error;

    })
  }


  getPhoneErrorMessage() {
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

  uploadImageData = new FormData();
  selectedFile: File;
  url;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    var reader = new FileReader();
    reader.onload = (event: any) => {
      console.log("here")
      this.url = event.target.result;
    };
    reader.onerror = (event: any) => {
      console.log("File could not be read: " + event.target.error.code);
    };
    reader.readAsDataURL(event.target.files[0]);
  }
  onUpload() {
    this.uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    console.log(this.uploadImageData)
  }


  // getImage() {
  //   this.httpClient.get('/api/get/' + this.imageName)
  //     .subscribe(
  //       res => {
  //         this.retrieveResonse = res;
  //         this.base64Data = this.retrieveResonse.picByte;
  //         this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
  //       }
  //     );
  // }

}
