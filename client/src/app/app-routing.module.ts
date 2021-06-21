import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactsComponent } from './contacts/contacts.component';
import { DoctorBlankComponent } from './doctor-blank/doctor-blank.component';
import { DoctorInfoComponent } from './doctor-info/doctor-info.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { FindDoctorComponent } from './find-doctor/find-doctor.component';
import { HomeComponent } from './home/home.component';
import { InfoComponent } from './info/info.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { UserInfoComponent } from './user-info/user-info.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'become/doctor', component: DoctorBlankComponent },
    { path: 'search/doctor', component: FindDoctorComponent },
    { path: 'doctors/specialty/:specialty', component: DoctorsComponent },
    { path: 'doctors/address/:address', component: DoctorsComponent },
    { path: 'doctors/name/:name', component: DoctorsComponent },
    { path: 'doctors/address/:address/specialty/:specialty', component: DoctorsComponent },
    { path: 'doctors/address/:address/name/:name', component: DoctorsComponent },
    { path: 'doctors/specialty/:specialty/name/:name', component: DoctorsComponent },
    { path: 'doctors/address/:address/specialty/:specialty/name/:name', component: DoctorsComponent },
    { path: 'doctors/all', component: DoctorsComponent },
    { path: 'user/info', component: UserInfoComponent },

    { path: 'doctor/:firstName/:middleName/:lastName/info', component: DoctorInfoComponent },
    { path: 'info', component: InfoComponent },
    { path: 'contacts', component: ContactsComponent },
    { path: '**', component: NotfoundComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }