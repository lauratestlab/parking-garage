import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {SignupModel} from "../model/signup-model";
import {response} from "express";
import {Router} from "@angular/router";
import {LotService} from "../service/lot.service";
import {LocalStorageService} from "../service/local-storage.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
      ReactiveFormsModule,
      CommonModule
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signupForm: FormGroup;
  msg: string | undefined;
  fb = inject(FormBuilder);
  // http = inject(HttpClient);

  constructor(
      private router: Router,
      private lotService: LotService
  ){
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      login: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  register(): void {
    // Extract form values into a SignupModel object
    const request: SignupModel = this.signupForm.value;
    console.log(request);

    if (this.signupForm.valid) {
      console.log("Form is valid");

      this.lotService.register(request).subscribe({
        next: () => {
          this.router.navigate(['activate']);
        },
        error: (err) => {
          console.error("Error Received: ", err);
        }
      });
    } else {
      console.log("Form submission failed");
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

  login(): void {
    this.router.navigate(['login']);
  }

}
