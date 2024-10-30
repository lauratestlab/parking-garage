import { Route } from '@angular/router';

import SignupComponent from './signup.component';

const signupRoute: Route = {
  path: 'register',
  component: SignupComponent,
  title: 'Sign up',
};

export default signupRoute;
