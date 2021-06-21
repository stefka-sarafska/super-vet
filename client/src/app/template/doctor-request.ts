export interface DoctorRequest{
    firstName: string;
    middleName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    specialty: string;
    description?: string;
    formation?: string;
    qualifications?: string;
    biography?: string
    appointments?:any[]
}