import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DoctorBlankComponent } from './doctor-blank/doctor-blank.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'become/doctor', component: DoctorBlankComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }