import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { UserService } from '../service/user.service';
import { User } from '../template/user';

@Component({
  selector: 'app-headers',
  templateUrl: './headers.component.html',
  styleUrls: ['./headers.component.css']
})
export class HeadersComponent implements OnInit {

  disableSelect = new FormControl(false);
  isLoggedIn: boolean;
  loggedUser: User;
  isInfo: boolean;

  constructor(public dialog: MatDialog, private userService: UserService, private router: Router) {
    this.isUserLoggedIn()
  }

  ngOnInit(): void {
    
  }

  openLoginDialog() {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '30%',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  logout() {
    this.userService.logout({ email: this.loggedUser.email, password: this.loggedUser.password })
      .subscribe(() => {
        console.log("in")
          localStorage.removeItem("User");
          this.router.navigate(["/"]);
          console.log("Logged out successfully!");
      })
  }

  isUserLoggedIn() {
    if (localStorage.getItem('User') !== null) {
      this.loggedUser = JSON.parse(localStorage.getItem('User'));
      this.isLoggedIn = true;
    } else {
      this.isLoggedIn = false
    }
  }

  info() {
    this.isInfo = true;
  }

}
