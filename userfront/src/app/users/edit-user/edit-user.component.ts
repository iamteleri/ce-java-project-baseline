import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user!: User

  constructor(private service: UserService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router) {}



  ngOnInit() {
    this.cargar();
  }

  cargar() {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.detail(id).subscribe(
      data => {
        this.user = data;
        console.log(data)
      }, err => {
        this.toastr.error('Error recuperando con id ' + id, 'Error', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.router.navigate(['/']);
      }
    )
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.update(this.user, id).subscribe( data => {
      this.toastr.success('Actualizado correctamente', '', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
      this.router.navigate(['/']);
    }, err => {
      this.toastr.error('Error actualizando usuario', 'Error', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
    })
  }

}
