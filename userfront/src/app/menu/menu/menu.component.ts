import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GeneratorService } from 'src/app/service/generator.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  isAdmin: boolean = false;
  isLogged: boolean = false;

  constructor(private service: GeneratorService,
    private toastr: ToastrService,
    private router: Router,
    private tokenService: TokenService) {}

    ngOnInit() {
      this.isAdmin = this.tokenService.isAdmin();
      console.log('isAdmin ' + this.isAdmin);
      this.isLogged = this.tokenService.isLogged();
      console.log('isLogged ' + this.isLogged);
    }

  create() {
    this.service.generate().subscribe( data => {
      this.toastr.success('Peticion de generación de usuario enviada correctamente. En breve estará disponible el nuevo usuario', 'Generado', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
      this.router.navigate(['/']);
    }, err => {
      this.toastr.error('Error generando usuario', 'Error', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
    })
  }

  logOut() {
    this.tokenService.logout();
    this.ngOnInit();
    this.router.navigate(['/login']);

  }
}
