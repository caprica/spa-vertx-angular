import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '..//model/user'

@Injectable()
export class UserService {

  serviceUrl: string = '/api/users';

  constructor(private http: HttpClient) {}

  getUsers() {
    return this.http.get<User[]>(this.serviceUrl);
  }

  getUser(username: string) {
    return this.http.get<User>(`${this.serviceUrl}/${username}`);
  }

}
