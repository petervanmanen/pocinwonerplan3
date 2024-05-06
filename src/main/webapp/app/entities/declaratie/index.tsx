import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Declaratie from './declaratie';
import DeclaratieDetail from './declaratie-detail';
import DeclaratieUpdate from './declaratie-update';
import DeclaratieDeleteDialog from './declaratie-delete-dialog';

const DeclaratieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Declaratie />} />
    <Route path="new" element={<DeclaratieUpdate />} />
    <Route path=":id">
      <Route index element={<DeclaratieDetail />} />
      <Route path="edit" element={<DeclaratieUpdate />} />
      <Route path="delete" element={<DeclaratieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeclaratieRoutes;
