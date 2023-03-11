import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Loginintoapp } from 'src/app/models/loginintoapp';
import { AuthService } from 'src/app/service/auth.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username!: string;
  password!: string;

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private toastrService: ToastrService,
    private router: Router
    ) {}
  
  ngOnInit(): void {
    
  }

  onLogin(): void {
    const dto = new Loginintoapp(this.username, this.password);
    this.authService.login(dto).subscribe(
      data => {
        this.tokenService.logout();
        this.tokenService.setToken(data.token);
        this.router.navigate(['/home']);
        location.reload(); 
      }, err => {
        this.toastrService.error('Error iniciando sesion', 'Error', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }

}
