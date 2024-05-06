import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tentoonstelling from './tentoonstelling';
import TentoonstellingDetail from './tentoonstelling-detail';
import TentoonstellingUpdate from './tentoonstelling-update';
import TentoonstellingDeleteDialog from './tentoonstelling-delete-dialog';

const TentoonstellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tentoonstelling />} />
    <Route path="new" element={<TentoonstellingUpdate />} />
    <Route path=":id">
      <Route index element={<TentoonstellingDetail />} />
      <Route path="edit" element={<TentoonstellingUpdate />} />
      <Route path="delete" element={<TentoonstellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TentoonstellingRoutes;
