import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Raadsstuk from './raadsstuk';
import RaadsstukDetail from './raadsstuk-detail';
import RaadsstukUpdate from './raadsstuk-update';
import RaadsstukDeleteDialog from './raadsstuk-delete-dialog';

const RaadsstukRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Raadsstuk />} />
    <Route path="new" element={<RaadsstukUpdate />} />
    <Route path=":id">
      <Route index element={<RaadsstukDetail />} />
      <Route path="edit" element={<RaadsstukUpdate />} />
      <Route path="delete" element={<RaadsstukDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RaadsstukRoutes;
