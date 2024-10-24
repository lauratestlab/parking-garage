import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {SignupModel} from "../model/signup-model";
import {Router} from "@angular/router";
import {LotService} from "../service/lot.service";
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

  login(): void {
    this.router.navigate(['login']);
  }

}
