import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verkeersdrempel from './verkeersdrempel';
import VerkeersdrempelDetail from './verkeersdrempel-detail';
import VerkeersdrempelUpdate from './verkeersdrempel-update';
import VerkeersdrempelDeleteDialog from './verkeersdrempel-delete-dialog';

const VerkeersdrempelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verkeersdrempel />} />
    <Route path="new" element={<VerkeersdrempelUpdate />} />
    <Route path=":id">
      <Route index element={<VerkeersdrempelDetail />} />
      <Route path="edit" element={<VerkeersdrempelUpdate />} />
      <Route path="delete" element={<VerkeersdrempelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerkeersdrempelRoutes;
