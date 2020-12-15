import { UserService } from '../_services/user.service';
import { User } from '../_models/user';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { RestErrorInfo } from '../_models/rest.error.info';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  user: User = new User();
  submitted;
  errorMessage = '';

  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit() {
    this.submitted = false;
  }

  save() {
    this.userService
    .createUser(this.user).subscribe(data => {
      console.log(data)
      this.user = new User();
      this.gotoList();
    },
    (err) => {
      console.log(err);
      this.errorMessage = 'User creation failed: '+err.toString();
      console.log(this.errorMessage);
   })
  }

  onSubmit() {
    this.errorMessage = '';
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/users']);
  }
}
