import { AppointmentsComponent } from "../appointments/appointments.component";

export interface DoctorTemplate {
    firstName: string;
    middleName: string;
    lastName: string;
    address: string;
    specialty: string;
    phoneNumber: string;
    email: string;
    password?: string;
    image: any;
    description?: string;
    formation?: string;
    qualifications?: string;
    biography?: string
    appintment?:any[]
}