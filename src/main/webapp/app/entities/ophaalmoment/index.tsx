import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ophaalmoment from './ophaalmoment';
import OphaalmomentDetail from './ophaalmoment-detail';
import OphaalmomentUpdate from './ophaalmoment-update';
import OphaalmomentDeleteDialog from './ophaalmoment-delete-dialog';

const OphaalmomentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ophaalmoment />} />
    <Route path="new" element={<OphaalmomentUpdate />} />
    <Route path=":id">
      <Route index element={<OphaalmomentDetail />} />
      <Route path="edit" element={<OphaalmomentUpdate />} />
      <Route path="delete" element={<OphaalmomentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OphaalmomentRoutes;
