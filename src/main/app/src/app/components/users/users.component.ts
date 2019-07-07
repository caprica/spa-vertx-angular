import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx'
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

import { User } from '../../model/user'
import { UserService } from '../../services/user.service'

@Component({
  selector: 'users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users$: Observable<User[]>;
  error: any;

  constructor(private userService: UserService) {}

  getUsers(): void {
    this.error = null;
    this.users$ = this.userService.getUsers().pipe(
      catchError(err => {
        this.error = err;
        return throwError(err);
      })
    );
  }

  ngOnInit(): void {
    this.getUsers();
  }

}
