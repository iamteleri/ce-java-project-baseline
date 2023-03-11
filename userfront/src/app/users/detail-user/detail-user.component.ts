import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-detail-user',
  templateUrl: './detail-user.component.html',
  styleUrls: ['./detail-user.component.css']
})
export class DetailUserComponent implements OnInit {

  user!: User
  isAdmin: boolean = false;

  constructor(private service: UserService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private tokenService: TokenService) {}



  ngOnInit() {
    this.isAdmin = this.tokenService.isAdmin();
    this.cargar();
  }

  cargar() {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.detail(id).subscribe(
      data => {
        this.user = data;
        console.log(data)
      }, err => {
        this.toastr.error('Error recuperando con id ' + id, 'Fail', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.tokenService.logout();
        this.router.navigate(['/']);
      }
    )
  }

  delete(id: string): void {
    this.service.delete(id).subscribe(
      data => {
        this.toastr.success('Usuario eliminado correctamente', '', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.router.navigate(['/']);
      }, err => {
        console.log('Error: ' + err)
      }
    )
  }

}
