import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../template/user';


@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    register(user: User) {
        return this.http.post("/api/register", user);
    }

    login(user:User){
        return this.http.post("/api/login", user);
    }

    logout(user:User){
        return this.http.post("/api/logout", user);
    }

}