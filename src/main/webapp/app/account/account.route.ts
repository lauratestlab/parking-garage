import { Routes } from '@angular/router';

import activateRoute from './activate/activate.route';
import passwordRoute from './password/password.route';
import passwordResetFinishRoute from './password-reset/finish/password-reset-finish.route';
import passwordResetInitRoute from './password-reset/init/password-reset-init.route';
import signupRoute from './signup/signup.route';
import settingsRoute from './settings/settings.route';

const accountRoutes: Routes = [
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  passwordResetInitRoute,
  signupRoute,
  settingsRoute,
];

export default accountRoutes;
