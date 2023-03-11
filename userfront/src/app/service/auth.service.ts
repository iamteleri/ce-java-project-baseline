import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Jwttoken } from '../models/jwttoken';
import { Loginintoapp } from '../models/loginintoapp';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  authUrl = 'http://localhost:8082/auth';

  public login(dto: Loginintoapp): Observable<Jwttoken> {
    return this.httpClient.post<Jwttoken>(this.authUrl, dto);
  }

}


