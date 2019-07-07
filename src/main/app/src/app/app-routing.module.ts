import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent     } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { UserComponent     } from './components/user/user.component';
import { UsersComponent    } from './components/users/users.component';

import { BrowserModule } from '@angular/platform-browser';

const routes: Routes = [
  { path: 'home'           , component : HomeComponent                        },
  { path: 'users'          , component : UsersComponent                       },
  { path: 'users/:username', component : UserComponent                        },
  { path: ''               , redirectTo: '/home'          , pathMatch: 'full' },
  { path: '**'             , component : NotFoundComponent                    }
];

@NgModule({
  declarations: [
    NotFoundComponent,
    HomeComponent,
    UserComponent,
    UsersComponent
  ],
  imports: [RouterModule.forRoot(routes), BrowserModule],
  exports: [RouterModule]
})
export class AppRoutingModule {}
