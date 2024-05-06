import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Afvalbak from './afvalbak';
import AfvalbakDetail from './afvalbak-detail';
import AfvalbakUpdate from './afvalbak-update';
import AfvalbakDeleteDialog from './afvalbak-delete-dialog';

const AfvalbakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Afvalbak />} />
    <Route path="new" element={<AfvalbakUpdate />} />
    <Route path=":id">
      <Route index element={<AfvalbakDetail />} />
      <Route path="edit" element={<AfvalbakUpdate />} />
      <Route path="delete" element={<AfvalbakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AfvalbakRoutes;
