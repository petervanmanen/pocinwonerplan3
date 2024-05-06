import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Raadslid from './raadslid';
import RaadslidDetail from './raadslid-detail';
import RaadslidUpdate from './raadslid-update';
import RaadslidDeleteDialog from './raadslid-delete-dialog';

const RaadslidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Raadslid />} />
    <Route path="new" element={<RaadslidUpdate />} />
    <Route path=":id">
      <Route index element={<RaadslidDetail />} />
      <Route path="edit" element={<RaadslidUpdate />} />
      <Route path="delete" element={<RaadslidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RaadslidRoutes;
