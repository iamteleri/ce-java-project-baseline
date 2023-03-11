import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { UserPage } from '../models/userpage';
import { Filter } from '../models/filter';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  userURL = 'http://localhost:8082/api';

  constructor(private httpClient: HttpClient) { }

  public lista(page: number, filter: Filter): Observable<UserPage> {
    var url = this.userURL + '?page=' + page;
    if (filter.email != '') {
      url = url + '&email=' + filter.email;
    }
    if (filter.name != '') {
      url = url + '&name=' + filter.name;
    }
    if (filter.lastname != '') {
      url = url + '&lastname=' + filter.lastname;
    }
    if (filter.nationality != '') {
      url = url + '&nat=' + filter.nationality;
    }
    return this.httpClient.get<UserPage>(url);
  }

  public detail(id: string): Observable<User> {
    return this.httpClient.get<User>(this.userURL + `/${id}`);
  }

  public save(user: User): Observable<any> {
    return this.httpClient.post<any>(this.userURL, user)
  }

  public update(user: User, id: string): Observable<any> {
    return this.httpClient.put<any>(this.userURL + `/${id}`, user)
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete<any>(this.userURL + `/${id}`);
  }

}
