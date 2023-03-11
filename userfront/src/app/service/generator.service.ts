import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from '../models/user';


@Injectable({
  providedIn: 'root'
})
export class GeneratorService {

  generateURL = 'http://localhost:8081/generate';

  constructor(private httpClient: HttpClient) { }



  public generate(): Observable<any> {
    return this.httpClient.post<any>(this.generateURL, null)
  }



}
