import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs/Rx'
import { catchError, switchMap } from 'rxjs/operators';
import { throwError } from 'rxjs';

import { User } from '../../model/user'
import { UserService } from '../../services/user.service'

@Component({
  selector: 'user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  username: string;
  user$: Observable<User>;
  error: any;

  constructor(private route: ActivatedRoute, private userService: UserService) {}

  getUser(): void {
    this.error = null;
    this.user$ = this.route.paramMap.pipe(
      // You know, all I wanted was the username parameter for the route, but I have to do THIS to get it!? For real!?!?
      switchMap((params: ParamMap) => {
        this.username = params.get('username');
        return this.userService.getUser(this.username).pipe(
          catchError(err => {
            this.error = err;
            return throwError(err);
          })
        );
      })
    );
  }

  ngOnInit(): void {
    this.getUser();
  }

}
