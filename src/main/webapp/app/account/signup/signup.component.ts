import {Component, inject, signal} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Router, RouterModule} from '@angular/router';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';


import {EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE} from 'app/config/error.constants';
import SharedModule from 'app/shared/shared.module';
import PasswordStrengthBarComponent from '../password/password-strength-bar/password-strength-bar.component';
import {SignupService} from './signup.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [SharedModule, RouterModule, FormsModule, ReactiveFormsModule, PasswordStrengthBarComponent],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export default class SignupComponent {

  doNotMatch = signal(false);
  error = signal(false);
  errorEmailExists = signal(false);
  errorUserExists = signal(false);
  success = signal(false);

  signupForm = new FormGroup({
    firstName: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
      ],
    }),
    lastName: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
      ],
    }),
    login: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    email: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    password: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
    confirmPassword: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
  });

  constructor(private router: Router){}

  private registerService = inject(SignupService);

  register(): void {
    this.doNotMatch.set(false);
    this.error.set(false);
    this.errorEmailExists.set(false);
    this.errorUserExists.set(false);

    const { password, confirmPassword } = this.signupForm.getRawValue();
    if (password !== confirmPassword) {
      this.doNotMatch.set(true);
    } else {
      const { login, email, firstName, lastName } = this.signupForm.getRawValue();
      this.registerService
          .save({ login, email, password, firstName, lastName })
          .subscribe({ next: () => this.success.set(true), error: response => this.processError(response) });
    }

    // // Extract form values into a SignupModel object
    // const request: SignupModel = this.signupForm.value;
    // console.log(request);
    //
    // if (this.signupForm.valid) {
    //   console.log("Form is valid");
    //
    //   this.lotService.register(request).subscribe({
    //     next: () => {
    //       this.router.navigate(['activate']);
    //     },
    //     error: (err) => {
    //       console.error("Error Received: ", err);
    //     }
    //   });
    // } else {
    //   console.log("Form submission failed");
    // }

  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists.set(true);
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists.set(true);
    } else {
      this.error.set(true);
    }
  }


  // register(): void {
  //   this.storage.remove('auth-key');
  //
  //   const formValue = this.form.value;
  //   // @ts-ignore
  //   this.request.first_name = formValue.first_name;
  //   // if (this.form.valid) {
  //   //   alert("You've been registered!");
  //   //
  //   //   this.http.post<SignupModel>('http://localhost:8080/api/register', {
  //   //     user: this.form.getRawValue()
  //   //   }).subscribe({
  //   //     next: (response) => {
  //   //       console.log('response', response);  // Log the response here
  //   //       this.router.navigate(['activate']);  // Navigate to the 'activate' page
  //   //     },
  //   //     error: (err) => {
  //   //       console.error('Error during registration:', err);  // Log the error if any
  //   //     },
  //   //     complete: () => {
  //   //       console.log('Registration request completed.');  // Optionally handle completion
  //   //     }
  //   //   });
  //   //
  //   // } else {
  //   //   alert("Please fill out all fields correctly.");
  //   // }

  // login(): void {
  //   this.router.navigate(['login']);
  // }
  login(): void {
    this.router.navigate(['login']);
  }
}
