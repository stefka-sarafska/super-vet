import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DoctorService } from '../service/doctor.service';
import { DoctorTemplate } from '../template/doctor';

@Component({
  selector: 'app-find-doctor',
  templateUrl: './find-doctor.component.html',
  styleUrls: ['./find-doctor.component.css']
})
export class FindDoctorComponent implements OnInit {

  url: string = "/doctors";
  specialties = new Set();
  addresses = new Set();

  constructor(private doctorService: DoctorService, private router: Router) { }

  ngOnInit(): void {
    this.getDoctorsSpecialties();
    this.getDoctorsAddresses();
  }

  onSearch(address:string,specialty:string,name:string) {
    this.createUrl(address,specialty,name);
    this.router.navigateByUrl(this.url);
  }


  getDoctorsSpecialties() {
    this.doctorService.getAllDoctorsSortedBySpecialty().subscribe((doctors: DoctorTemplate[]) => {
      doctors.forEach(doctor => {
        this.specialties.add(doctor.specialty);
      })
    })
  }

  getDoctorsAddresses() {
    this.doctorService.getAllDoctorsSortedByAddress().subscribe((doctors: DoctorTemplate[]) => {
      doctors.forEach(doctor => {
        this.addresses.add(doctor.address);
      })
    })
  }

  private createUrl(address:string,specialty:string,name:string) {
    if (address !== undefined && specialty === undefined && name === "") {
      this.url += "/address/" + address;
    } else if (address === undefined && specialty !== undefined && name === "") {
      this.url += "/specialty/" + specialty;
    } else if (address === undefined && specialty === undefined && name !== "") {
      this.url += "/name/" + name;
    } else if (address !== undefined && specialty !== undefined && name === "") {
      this.url += "/address/" + address + "/specialty/" + specialty;
    } else if (address !== undefined && specialty === undefined && name !== "") {
      this.url += "/address/" + address + "/name/" + name;
    } else if (address === undefined && specialty !== undefined && name === "") {
      this.url += "/specialty/" + specialty + "/name/" + name;
    } else if (address !== undefined && specialty !== undefined && name !== "") {
      this.url += "/address/" + address + "/specialty/" + specialty + "/name/" + name;
    }else if(address === undefined && specialty === undefined && name === ""){
      this.url += "/all";
    }
  }

}
