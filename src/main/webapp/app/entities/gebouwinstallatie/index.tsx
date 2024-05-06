import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebouwinstallatie from './gebouwinstallatie';
import GebouwinstallatieDetail from './gebouwinstallatie-detail';
import GebouwinstallatieUpdate from './gebouwinstallatie-update';
import GebouwinstallatieDeleteDialog from './gebouwinstallatie-delete-dialog';

const GebouwinstallatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebouwinstallatie />} />
    <Route path="new" element={<GebouwinstallatieUpdate />} />
    <Route path=":id">
      <Route index element={<GebouwinstallatieDetail />} />
      <Route path="edit" element={<GebouwinstallatieUpdate />} />
      <Route path="delete" element={<GebouwinstallatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebouwinstallatieRoutes;
