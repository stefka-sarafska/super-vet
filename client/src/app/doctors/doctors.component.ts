import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DoctorService } from '../service/doctor.service';
import { DoctorTemplate } from '../template/doctor';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent {

  specialty: string;
  address: string;
  name: string;
  doctorImage:any;

  foundDoctors: DoctorTemplate[];

  constructor(private activatedRoute: ActivatedRoute, private doctorService: DoctorService, private router: Router) { }

  ngOnInit(): void {
    this.specialty = this.activatedRoute.snapshot.paramMap.get("specialty")
    this.address = this.activatedRoute.snapshot.paramMap.get("address")
    this.name = this.activatedRoute.snapshot.paramMap.get("name")
    this.getDoctors();
  }

  getDoctors() {
    if (this.address !== null && this.specialty === null && this.name === null) {
      this.getDoctorsByAddress(this.address);
    } else if (this.address === null && this.specialty !== null && this.name === null) {
      this.getDoctorsBySpecialty(this.specialty);
    } else if (this.address === null && this.specialty === null && this.name !== null) {
      this.getAllDoctorsByName(this.name)
    } else if (this.address !== null && this.specialty !== null && this.name === null) {
      this.getAllDoctorsByAddressAndSpecialty(this.address, this.specialty);
    } else if (this.address !== null && this.specialty === null && this.name !== null) {
      this.getDoctorsByAddressAndName(this.address, this.name);
    } else if (this.address === null && this.specialty !== null && this.name !== null) {
      this.getDoctorsBySpecialtyAndName(this.specialty, this.name)
    } else if (this.address !== null && this.specialty !== null && this.name !== null) {
      this.getDoctorsBySpecialtyAddressAndName(this.specialty, this.address, this.name)
    } else {
      this.getAllDoctors()
    }
  }

  getAllDoctors() {
    this.doctorService.getAllDoctors().subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors;
    }, error => {
      console.log(error);
    });
  }

  getAllDoctorsByName(name: string) {
    this.doctorService.getDoctorByName(name).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors;
    }, error => {
      console.log(error);
    })
  }

  getAllDoctorsByAddressAndSpecialty(address: string, specialty: string) {
    this.doctorService.getAllDoctorsByAddressAndSpecialty(specialty, address).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors;
    }, error => {
      console.log(error);
    })
  }


  getDoctorsBySpecialty(specialty: string) {
    this.doctorService.getAllDoctorsBySpecialty(specialty).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors;
    }, error => {
      console.log(error);
    })
  }

  getDoctorsByAddress(address: string) {
    this.doctorService.getAllDoctorsByAddress(address).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors;
      console.log(doctors)
    }, error => {
      console.log(error);
    })
  }

  getDoctorsByAddressAndName(address: string, name: string) {
    this.doctorService.getDoctorsByAddressAndName(address, name).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors
    }, error => {
      console.log(error);
    })
  }


  getDoctorsBySpecialtyAndName(specialty: string, name: string) {
    this.doctorService.getDoctorsBySpecialtyAndName(specialty, name).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors
    }, error => {
      console.log(error);
    })
  }

  getDoctorsBySpecialtyAddressAndName(specialty: string, address: string, name: string) {
    this.doctorService.getDoctorsBySpecialtyAddressAndName(specialty, address, name).subscribe((doctors: DoctorTemplate[]) => {
      this.foundDoctors = doctors
    }, error => {
      console.log(error);
    })
  }

  onSubmit(firstName: string, middleName: string, lastName: string, email: string) {
    let url = "/doctor/" + firstName + "/" + middleName + "/" + lastName + "/info"
    let fullName = firstName + " " + middleName + " " + lastName;
    this.router.navigateByUrl(url, { state: { email: email } })
  }


















  // constructor(private httpClient: HttpClient, private sanitizer: DomSanitizer) { }
  // selectedFile: File;
  // retrievedImage: any;
  // base64Data: any;
  // retrieveResonse: any;
  // message: string;
  // imageName: any;
  // //Gets called when the user selects an image
  // public onFileChanged(event) {
  //   //Select File
  //   this.selectedFile = event.target.files[0];
  // }
  // //Gets called when the user clicks on submit to upload the image
  // onUpload() {
  //   console.log(this.selectedFile);

  //   //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
  //   const uploadImageData = new FormData();
  //   uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

  //   //Make a call to the Spring Boot Application to save the image
  //   this.httpClient.post('/api/upload', uploadImageData, { observe: 'response' })
  //     .subscribe((response) => {
  //       if (response.status === 200) {
  //         this.message = 'Image uploaded successfully';
  //       } else {
  //         this.message = 'Image not uploaded successfully';
  //       }
  //     }
  //     );
  // }
  //   //Gets called when the user clicks on retieve image button to get the image from back end
  //   getImage() {
  //   //Make a call to Sprinf Boot to get the Image Bytes.
  //   this.httpClient.get('/api/get/' + this.imageName)
  //     .subscribe(
  //       (res:any) => {
  //         console.log(res)
  //         // this.retrieveResonse = res;
  //         // this.base64Data = this.retrieveResonse.picByte;
  //         // this.retrievedImage = 'data:image/jpg;base64,' + this.base64Data;
  //         let objectURL = 'data:image/jpeg;base64,' + res.image;

  //         this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl(objectURL);
  //       }
  //     );
  // }

}
