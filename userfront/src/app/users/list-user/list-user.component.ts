import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Filter } from 'src/app/models/filter';
import { User } from 'src/app/models/user';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';


@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {


  currentPage: number = 0;
  totalSize!: number;
  lastPage!: number; 
  previousPage!: number;
  users: User[] = [];

  email : string = '';
  name : string = '';
  lastname : string = '';
  nationality : string = '';

  isAdmin: boolean = false;
  
  
  constructor(private service: UserService,
    private toastr: ToastrService,
    private router: Router,
    private tokenService: TokenService) {}

  ngOnInit() {
    if (this.tokenService.isAdmin()) {
    this.listUsers(this.currentPage);
    } else {
      const id = this.tokenService.getUserId();
      this.router.navigate(['detail/' + id]);
    }
  }

  clear() {
    this.name = '';
    this.email = '';
    this.lastname = '';
    this.nationality = '';
  }

  listUsers(page: number): void {
    this.service.lista(page, new Filter(this.name, this.email, this.lastname, this.nationality)).subscribe(
      data => {
        this.users = data.content;
        this.previousPage = data.pageable.pageNumber;
        this.currentPage = data.pageable.pageNumber + 1;
        this.totalSize = data.totalElements;
        this.lastPage = data.totalPages;
        this.previousPage = data.pageable.pageNumber - 1;
      }, err => {
        console.log(err);
      }
    );
  }

  delete(id: string): void {
    this.service.delete(id).subscribe(
      data => {
        this.toastr.success('Usuario eliminado correctamente', '', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.router.navigate(['/']);
        this.listUsers(0);
      }, err => {
        console.log('Error: ' + err)
      }
    )
  }

  onUpdate(): void {
   this.listUsers(0);
  }

}
