import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Heffingsverordening from './heffingsverordening';
import HeffingsverordeningDetail from './heffingsverordening-detail';
import HeffingsverordeningUpdate from './heffingsverordening-update';
import HeffingsverordeningDeleteDialog from './heffingsverordening-delete-dialog';

const HeffingsverordeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Heffingsverordening />} />
    <Route path="new" element={<HeffingsverordeningUpdate />} />
    <Route path=":id">
      <Route index element={<HeffingsverordeningDetail />} />
      <Route path="edit" element={<HeffingsverordeningUpdate />} />
      <Route path="delete" element={<HeffingsverordeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeffingsverordeningRoutes;
