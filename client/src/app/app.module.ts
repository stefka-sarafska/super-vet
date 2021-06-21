import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { HomeComponent } from './home/home.component';
import { HeadersComponent } from './headers/headers.component';
import { DoctorBlankComponent } from './doctor-blank/doctor-blank.component';
import { AppRoutingModule } from './app-routing.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';
import { FindDoctorComponent } from './find-doctor/find-doctor.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { MatCardModule } from '@angular/material/card';
import { DoctorInfoComponent } from './doctor-info/doctor-info.component';
import { MatDialogModule } from '@angular/material/dialog';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AppointmentsComponent } from './appointments/appointments.component';
import { MatMenuModule } from '@angular/material/menu';
import { UserInfoComponent } from './user-info/user-info.component';
import { DatePipe } from '@angular/common';
import { BookAppointmentComponent } from './book-appointment/book-appointment.component';
import { MatTableModule } from '@angular/material/table';
import { NotfoundComponent } from './notfound/notfound.component';
import { InfoComponent } from './info/info.component';
import { ContactsComponent } from './contacts/contacts.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeadersComponent,
    DoctorBlankComponent,
    FindDoctorComponent,
    DoctorsComponent,
    DoctorInfoComponent,
    LoginComponent,
    RegisterComponent,
    AppointmentsComponent,
    UserInfoComponent,
    BookAppointmentComponent,
    NotfoundComponent,
    InfoComponent,
    ContactsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    AppRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule,
    MatCardModule,
    FormsModule,
    MatDialogModule,
    MatMenuModule,
    MatTableModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
