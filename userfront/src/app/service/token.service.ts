import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthTpken';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  public setToken(token: string): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  public logout(): void {
    localStorage.removeItem(TOKEN_KEY);
  }

  public isLogged(): boolean {
    return this.getToken() != null;
  }

  public isAdmin(): boolean {
    if (!this.isLogged()){
      return false;
    } else {
      const token = this.getToken();
      const payload = token!.split(".")[1];
      const decoded = atob(payload);
      const values = JSON.parse(decoded);
      const roles = values.roles;
      console.log(values);
      if (roles.indexOf('ROLE_ADMIN') < 0) {
        return false;
      } else {
        return true;
      }
    }
  }

  public getUserId(): string {
    if (!this.isLogged()){
      return '';
    } else {
      const token = this.getToken();
      const payload = token!.split(".")[1];
      const decoded = atob(payload);
      const values = JSON.parse(decoded);
      return values.id;
    }
  }

}
